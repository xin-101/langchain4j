<template>
  <view class="container">
    <!--聊天消息区域-->
    <scroll-view class="chat-area" scroll-y :scroll-top="scrollTop" scroll-with-animation
                 :scroll-into-view="scrollIntoView" @scroll="onScroll">
      <view class="message-list">
        <view v-for="(message,index) in messages" :key="index" :id="'msg-'+index" class="message-item"
              :class="message.type">
          <!--助手消息：头像在左边-->
          <view v-if="message.type==='assistant'" class="avatar assistant-avatar">
            <text class="avatar-icon">🔍</text>
          </view>

          <view class="message-content" :class="message.type">
            <view class="message-bubble" :class="message.type">
              <text class="message-text">{{ message.content }}</text>
              <text class="message-time">{{ message.time }}</text>
            </view>
          </view>

          <!--用户消息：头像在右边-->
          <view v-if="message.type==='user'" class="avatar user-avatar">
            <text class="avatar-icon">👤</text>
          </view>
        </view>
        <!--底部占位元素，用于滚动定位-->
        <view id="bottom-anchor" class="bottom-anchor"></view>
      </view>
    </scroll-view>

    <!--快捷操作按钮-->
    <view class="quick-actions" v-if="showQuickActions">
      <text class="quick-title">常用功能</text>
      <view class="action-buttons">
        <button class="quick-btn" @click="selectAction('lost')">
          <text class="btn-icon">📱</text>
          <text class="btn-text">我丢了东西</text>
        </button>
        <button class="quick-btn" @click="selectAction('found')">
          <text class="btn-icon">👛</text>
          <text class="btn-text">我捡到东西</text>
        </button>
        <button class="quick-btn" @click="selectAction('search')">
          <text class="btn-icon">🔍</text>
          <text class="btn-text">查询失物</text>
        </button>
      </view>
    </view>

    <!--输入区域-->
    <view class="input-area">
      <view class="input-container">
        <input class="message-input" v-model="inputText" placeholder="请描述您的失物情况..." @confirm="sendMessage" @input="onInput"
               confirm-type="send"/>
        <button class="send-btn" :class="{ 'active':inputText.trim() }" @click="sendMessage"
                :disabled="!inputText.trim()">
          <text class="send-icon">✈</text>
        </button>
      </view>
    </view>
  </view>
</template>

<script>
import { apiConfig } from '@/utils/apiConfig.js'

export default {
  data() {
    return {
      messages: [],
      inputText: '',
      scrollTop: 0,
      scrollIntoView: '',
      showQuickActions: true,
      userId: '',
      scrollTimer: null,
      scrollDebounceTimer: null,
      maxMessages: 50 // 限制最大消息数量
    }
  },
  onLoad() {
    this.userId = uni.getStorageSync('userId') || '用户';
    this.initChat();
  },
  methods: {
    initChat() {
      //初始化欢迎消息
      const welcomeMessage = {
        type: 'assistant',
        content: '您好!我是失物找领专属助手\n\n我能为您提供以下服务：\n·丢失物品信息登记\n·捡到物品信息登记\n·失物信息查询匹配\n·认领流程指导\n\n请告诉我您遇到的情况,我会耐心为您提供专业的失物招领服务~',
        time: this.getCurrentTime()
      };
      this.addMessage(welcomeMessage);
      this.scrollToBottom();
    },

    selectAction(action) {
      let message = '';
      switch (action) {
        case 'lost':
          message = '我丢了东西，需要登记失物信息';
          break;
        case 'found':
          message = '我捡到了东西，想要登记招领';
          break;
        case 'search':
          message = '我想查询是否有人捡到我的物品';
          break;
      }
      this.inputText = message;
      this.sendMessage();
      this.showQuickActions = false;
    },

    async sendMessage() {
      if (!this.inputText.trim()) return;

      // 防止重复发送
      if (this.isLoading) return;
      
      this.isLoading = true;

      //添加用户信息
      const userMessage = {
        type: 'user',
        content: this.inputText.trim(),
        time: this.getCurrentTime()
      };
      this.addMessage(userMessage);

      //保存用户输入
      const userInput = this.inputText.trim();

      //清空输入框
      this.inputText = '';

      //立即滚动到底部显示用户消息
      this.$nextTick(() => {
        this.scrollToBottom();
      });

      //添加一个正在输入的占位信息
      const typingMessage = {
        type: 'assistant',
        content: '正在思考...',
        time: this.getCurrentTime(),
        isTyping: true
      };
      this.messages.push(typingMessage);
      
      // 确保在下一个tick滚动到底部
      this.$nextTick(() => {
        this.scrollToBottom();
      });

      try {
        //调用后端API获取流式响应
        await this.callChatAPI(userInput);
      } catch (error) {
        console.error('API调用失败', error);
        //移除正在输入的消息
        this.messages = this.messages.filter(msg => !msg.isTyping);

        //显示错误信息
        const errorMessage = {
          type: 'assistant',
          content: error.message || '抱歉，我无法处理您的请求。请稍后再试。',
          time: this.getCurrentTime()
        };
        this.addMessage(errorMessage);
        this.$nextTick(() => {
          this.scrollToBottom();
        });
      } finally {
        this.isLoading = false;
      }
    },
    async callChatAPI(message) {
      const url = `${apiConfig.baseURL}/api/lost-and-found/recognize-intent?userId=${encodeURIComponent(this.userId)}&message=${encodeURIComponent(message)}`;

      //移除正在输入的消息
      this.messages = this.messages.filter(msg => !msg.isTyping);

      //创建助手消息，初始化内容
      const assistantMessage = {
        type: 'assistant',
        content: '',
        time: this.getCurrentTime()
      };
      this.addMessage(assistantMessage);
      const messageIndex = this.messages.length - 1;

      try {
        console.log('请求URL:', url);

        // 使用uni.request获取完整响应
        const response = await new Promise((resolve, reject) => {
          uni.request({
            url: url,
            method: 'GET',
            header: {
              'Accept': 'text/plain; charset=UTF-8'
            },
            timeout: 30000,
            success: (res) => {
              console.log('API响应:', res);
              resolve(res);
            },
            fail: (err) => {
              console.error('API请求失败:', err);
              reject(err);
            }
          });
        });

        if (response.statusCode !== 200) {
          throw new Error(`HTTP error! status: ${response.statusCode}`);
        }

        //处理响应数据
        let responseText = '';
        if (typeof response.data === 'string') {
          responseText = response.data;
        } else if (response.data && typeof response.data === 'object') {
          responseText = JSON.stringify(response.data, null, 2);
        } else {
          responseText = '收到了响应，但内容格式不正确';
        }

        // 使用打字机效果显示响应
        await this.typeWriterEffect(messageIndex, responseText);

      } catch (error) {
        console.error('API调用失败', error);
        
        // 移除当前助手消息
        this.messages.splice(messageIndex, 1);

        let errorMessage = '抱歉，服务暂时不可用，请稍后再试。';
        if (error.statusCode == 406) {
          errorMessage = '请求格式不被接受，请检查API配置。';
        } else if (error.statusCode == 404) {
          errorMessage = 'API接口不存在，请检查后端服务是否正常运行。';
        } else if (error.errMsg) {
          if (error.errMsg.includes('timeout')) {
            errorMessage = '请求超时，请检查网络连接。';
          } else if (error.errMsg.includes('fail')) {
            errorMessage = '网络连接失败，请检查网络设置。后端地址：' + apiConfig.baseURL;
          }
        }
        
        const errorAssistantMessage = {
          type: 'assistant',
          content: errorMessage,
          time: this.getCurrentTime()
        };
        this.addMessage(errorAssistantMessage);
        this.scrollToBottom();
      }
    },
    
    // 输入事件处理
    onInput(event) {
      // 为了保持输入流畅性，这里可以添加一些优化逻辑
      // 目前暂时保留原逻辑，确保v-model正常工作
      this.inputText = event.detail.value;
    },
    
    // 打字机效果显示响应内容
    async typeWriterEffect(messageIndex, fullText) {
      // 确保目标消息存在
      if (messageIndex < 0 || messageIndex >= this.messages.length) {
        return;
      }
      
      // 重置内容
      this.messages[messageIndex].content = '';
      
      const chars = fullText.split('');
      
      // 使用更大的批次以提高性能
      const batchSize = 20; // 每次处理20个字符
      
      for (let i = 0; i < chars.length; i += batchSize) {
        // 构建当前批次的文本
        const batchEnd = Math.min(i + batchSize, chars.length);
        for (let j = i; j < batchEnd; j++) {
          this.messages[messageIndex].content += chars[j];
        }
        
        // 滚动到底部
        this.debounceScroll();
        
        // 添加延迟模拟打字机效果
        await new Promise(resolve => setTimeout(resolve, 50));
      }
    },


    // getCurrentTime() {
    //   const now = new Date();
    //   return `${now.getHours().toString().padStart(2,'0')}:${now.getMinutes().toString().padStart(2,'0'))}`;
    // },
    //

    //
    //
    //
    //
    //   //延迟显示助手回复，模拟思考时间
    //   setTimeout(() => {
    //     const assistantMessage = {
    //       type: 'assistant',
    //       content: response,
    //       time: this.getCurrentTime()
    //     };
    //     this.messages.push(assistantMessage);
    //     //助手回复后再次滚动到底部
    //     this.scrollToBottom();
    //   }, 800);
    // },
    //
    // generateResponse(userInput) {
    //   const input = userInput.toLowerCase();
    //
    //   //判断用户意图
    //   if (this.isLostItemIntent(input)) {
    //     return this.handleLostItem(userInput);
    //   } else if (this.isFoundItemIntent(userInput)) {
    //     return this.handleFoundItem(input)
    //   } else if (this.handleSearchIntent(userInput)) {
    //     return this.handleSearch(input);
    //   } else if (this.isOffTopicIntent(userInput)) {
    //     return "抱歉~我的工作主要围绕失物招领服务，你可以和我说说遗失物品/捡拾的相关信息"
    //   } else {
    //     return this.handleGeneralInquiry(userInput);
    //   }
    // },
    //
    // isLostItemIntent(input) {
    //   const keywords = ['丢了', '丢失', '遗失', '找不到', '不见了', '失物', '掉了'];
    //   return keywords.some(keyword => input.includes(keyword));
    // },
    //
    // isFoundItemIntent(input) {
    //   const keywords = ['捡到', '拾到', '发现', '招领', '捡了'];
    //   return keywords.some(keyword => input.includes(keyword))
    // },
    //
    // isSearchIntent(input) {
    //   const keywords = ['天气', '吃饭', '聊天', '你好', '再见', '谢谢'];
    //   return keywords.some(keyword => input.includes(keyword)) &&
    //       !this.isLostItemIntent(input) &&
    //       !this.isFoundItemIntent(input) &&
    //       !this.isSearchIntent(input);
    // },
    //
    // handleLostItem(input) {
    //   return '我来帮您登记失物信息\n\n为了更好的帮您找回物品，请提供以下信息：\n\n物品名称/类型：${this.extractItemType(input)}'
    // },
    //
    // handleFoundItem(input) {
    //   return '感谢您的拾金不昧\n\n为了更好的帮您找回物品，请提供以下信息：\n\n物品名称/类型：${this.extractItemType(input)}'
    // },
    //
    // handleSearch(input) {
    //   return '正在为您查询，请稍候...'
    // },
    //
    // handleGeneralInquiry(input) {
    //   return '抱歉，我无法理解您的问题。请重新描述您的问题或尝试使用其他功能。';
    // },
    //
    // extractItemType(input) {
    //   const items = ['手机', '钥匙', '包', '书', '书签', '银行卡', '身份证', '眼镜', '手表', '耳机', '充电器'];
    //   for (let item of items) {
    //     if (input.includes(item)) {
    //       return item;
    //     }
    //   }
    //   return null;
    // },
    getCurrentTime() {
      const now = new Date();
      return `${now.getHours().toString().padStart(2, '0')}:${now.getMinutes().toString().padStart(2, '0')}`;
    },

    scrollToBottom() {
      //清除之前的定时器
      if (this.scrollTimer) {
        clearTimeout(this.scrollTimer);
      }

      //使用更简单高效的滚动策略
      this.$nextTick(() => {
        // 设置scrollIntoView到页面底部的锚点元素
        this.scrollIntoView = 'bottom-anchor';
        
        // 简单地将scrollTop设置为一个很大的值，让滚动区域自动滚动到底部
        this.scrollTop = 999999;
      });
    },
    
    // 防抖滚动方法
    debounceScroll() {
      if (this.scrollDebounceTimer) {
        clearTimeout(this.scrollDebounceTimer);
      }
      
      // 设置100ms的防抖延迟，避免过于频繁的滚动影响输入体验
      this.scrollDebounceTimer = setTimeout(() => {
        this.scrollToBottom();
      }, 100);
    },
    
    // 添加消息并限制数量
    addMessage(message) {
      this.messages.push(message);
      
      // 如果消息数量超过限制，移除最旧的消息
      if (this.messages.length > this.maxMessages) {
        this.messages = this.messages.slice(-this.maxMessages);
      }
    },
  }
}
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background: #f5f5f5;
}

.chat-area {
  flex: 1;
  padding: 20rpx;
  overflow-y: auto;
}

.message-list {
  display: flex;
  flex-direction: column;
  gap: 24rpx;
}

.message-item {
  display: flex;
  align-items: flex-start;
  gap: 16rpx;
}

.message-item.assistant {
  justify-content: flex-start;
}

.message-item.user {
  justify-content: flex-end;
  flex-direction: row-reverse;
}

.avatar {
  width: 64rpx;
  height: 64rpx;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.assistant-avatar {
  background: #ff6b6b;
}

.user-avatar {
  background: #4a90e2;
}

.avatar-icon {
  font-size: 32rpx;
}

.message-content {
  max-width: 70%;
  display: flex;
  flex-direction: column;
}

.message-bubble {
  padding: 20rpx 24rpx;
  border-radius: 16rpx;
  word-wrap: break-word;
}

.message-bubble.assistant {
  background: #ffffff;
  border-top-left-radius: 4rpx;
}

.message-bubble.user {
  background: #4a90e2;
  color: #ffffff;
  border-top-right-radius: 4rpx;
}

.message-text {
  font-size: 28rpx;
  line-height: 1.6;
  white-space: pre-wrap;
}

.message-time {
  font-size: 20rpx;
  color: #999999;
  margin-top: 8rpx;
  text-align: right;
}

.message-bubble.user .message-time {
  color: rgba(255, 255, 255, 0.8);
}

.bottom-anchor {
  height: 1rpx;
}

.quick-actions {
  padding: 20rpx;
  background: #ffffff;
  border-top: 1rpx solid #e5e5e5;
}

.quick-title {
  font-size: 28rpx;
  font-weight: 600;
  color: #333333;
  margin-bottom: 16rpx;
  display: block;
}

.action-buttons {
  display: flex;
  gap: 16rpx;
  justify-content: space-between;
}

.quick-btn {
  flex: 1;
  background: #ffffff;
  border: 1rpx solid #e5e5e5;
  border-radius: 12rpx;
  padding: 24rpx;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12rpx;
}

.btn-icon {
  font-size: 40rpx;
}

.btn-text {
  font-size: 26rpx;
  color: #333333;
}

.input-area {
  background: #ffffff;
  border-top: 1rpx solid #e5e5e5;
  padding: 20rpx;
  padding-bottom: calc(20rpx + env(safe-area-inset-bottom));
}

.input-container {
  display: flex;
  align-items: center;
  gap: 16rpx;
}

.message-input {
  flex: 1;
  height: 72rpx;
  padding: 0 24rpx;
  background: #f5f5f5;
  border-radius: 36rpx;
  font-size: 28rpx;
}

.send-btn {
  width: 72rpx;
  height: 72rpx;
  border-radius: 50%;
  background: #c0c4cc;
  display: flex;
  align-items: center;
  justify-content: center;
  border: none;
}

.send-btn.active {
  background: #4a90e2;
}

.send-icon {
  font-size: 32rpx;
  color: #ffffff;
}
</style>