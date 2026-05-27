//流式请求工具
export class StreamRequest {

    /**
     * @param {string} url 请求地址
     * @param {Object} options 请求选项
     * @param {Function} onChunk 接收到数据块时的回调
     * @param {Function} onComplete 请求完成时的回调
     * @param {Function} onError 请求出错时的回调
     */
    static async request(url, options = {}, onChunk, onComlete, onError) {
        // 统一使用uni.request，适用于小程序环境
        try {
            const response = await new Promise((resolve, reject) => {
                uni.request({
                    url: url,
                    method: options.method || 'GET',
                    header: options.headers || {},
                    timeout: options.timeout || 30000,
                    success: resolve,
                    fail: reject
                });
            });

            if (response.statusCode !== 200) {
                throw new Error(`HTTP ${response.statusCode}`);
            }

            //获取
            let fullText = '';
            if (typeof response.data === 'string') {
                fullText = response.data;
            } else if (response.data) {
                fullText = JSON.stringify(response.data, null, 2);
            }

            // 模拟流式输出
            await StreamRequest.simulateStreaming(fullText, onChunk);

            if (onComlete) onComlete();

            return response.data;
        } catch (error) {
            console.error('流式请求失败', error);
            if (onError) onError(error);
        }
    }

    /**
     * 模拟流式输出效果
     * @param {string} text 待输出的文本
     * @param {Function} onChunk 每输出一个字符时的回调
     */
    static async simulateStreaming(text, onChunk) {
        if (!text || !onChunk)
            return;

        // 为了模拟流式效果，将文本分段发送
        const chars = text.split('');
        
        let accumulatedText = '';
        for (let i = 0; i < chars.length; i++) {
            accumulatedText += chars[i];
            
            // 每隔几个字符或遇到标点符号时发送当前累积的内容
            if (i % 20 === 0 || /[。！？，、；：\n]/.test(chars[i]) || i === chars.length - 1) {
                onChunk(accumulatedText);
                
                // 添加延迟模拟网络传输
                await new Promise(resolve => setTimeout(resolve, 10));
            }
        }
    }
}

export default StreamRequest;