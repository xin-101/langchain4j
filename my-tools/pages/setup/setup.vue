<template>
	<view class="container">
		<view class="header">
			<view class="logo-container">
				<view class="logo-icon">U</view>
			</view>
			<text class="welcome-title">欢迎使用智能助手</text>
			<text class="welcome-desc">请设置您的用户ID以开始使用</text>
		</view>

		<view class="form-section">
			<view class="input-group">
				<text class="label">用户ID</text>
				<input class="input" v-model="userId" placeholder="请输入您的用户ID" maxlength="20" @input="onInputChange" />
				<text class="hint">用户ID将用于个性化服务，请妥善保管</text>
			</view>

			<button class="submit-btn" :class="{'disable': !isValid}" :disabled="!isValid" @click="handleSubmit">
				开始使用
			</button>
		</view>
		
		<view class="footer">
			<text class="footer-text">智能助手v1.0.0</text>
		</view>
	</view>
</template>

<script>
	export default{
		data(){
			return{
				userId:'',
				isValid:false
			}
		},
		onLoad() {
			//检查是否已经设置过用户ID，如果已设置则显示在输入框中
			const savedUserId=uni.getStorageSync('userId');
			if(savedUserId){
				this.userId = savedUserId;
				this.isValid = true;
			}
		},
		methods:{
			onInputChange(){
				this.isValid=this.userId.trim().length>=1;
			},
			
			handleSubmit(){
				if(!this.isValid) return;
				
				//保存用户ID到本地存储
				uni.setStorageSync('userId',this.userId.trim());
				
				//显示成功提示
				uni.showToast({
					title:'设置成功',
					icon:'success',
					duration:1500
				});
				
				//延迟跳转到主页
				setTimeout(()=>{
					uni.redirectTo({
						url:'/pages/home/home'
					});
				},1500);
			}
		}
	}
</script>

<style scoped>
	.container{
		min-height: 100vh;
		background: linear-gradient(135deg,#667eea 0%,#764ba2 100%);
		display: flex;
		flex-direction: column;
		padding: 0 60rpx;
		box-sizing: border-box;
		color: #ffffff;
	}
	
	.header{
		flex: 1;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		text-align: center;
	}

	.logo-container{
		width: 160rpx;
		height: 160rpx;
		border-radius: 24rpx;
		background: #4ade80;
		display: flex;
		align-items: center;
		justify-content: center;
		box-shadow: 0 12rpx 28rpx rgba(0,0,0,0.25);
	}

	.logo-icon{
		font-size: 80rpx;
		font-weight: 700;
		color: #ffffff;
		font-family: Arial, sans-serif;
		letter-spacing: -4rpx;
	}

	.welcome-title{
		margin-top: 32rpx;
		font-size: 48rpx;
		font-weight: 700;
		letter-spacing: 2rpx;
	}

	.welcome-desc{
		margin-top: 12rpx;
		font-size: 26rpx;
		opacity: 0.85;
	}

	.form-section{
		padding: 40rpx 0;
		gap: 24rpx;
	}

	.input-group{
		display: flex;
		flex-direction: column;
		gap: 18rpx;
	}

	.label{
		font-size: 28rpx;
		opacity: 0.95;
	}

	.input{
		height: 88rpx;
		line-height: 88rpx;
		padding: 0 28rpx;
		border-radius: 16rpx;
		background: rgba(255,255,255,0.95);
		color: #333333;
		box-shadow: 0 10rpx 24rpx rgba(0,0,0,0.18);
	}

	.input::placeholder{
		color: rgba(0,0,0,0.35);
	}

	.hint{
		font-size: 24rpx;
		opacity: 0.85;
	}

	.submit-btn{
		width: 100%;
		height: 88rpx;
		border: none;
		border-radius: 16rpx;
		background: linear-gradient(135deg,#8f7de1 0%,#b299ff 100%);
		color: #ffffff;
		font-size: 32rpx;
		font-weight: 600;
		letter-spacing: 2rpx;
		box-shadow: 0 10rpx 20rpx rgba(0,0,0,0.18);
	}

	.submit-btn.disable{
		background: rgba(255,255,255,0.35);
		color: rgba(255,255,255,0.9);
		box-shadow: none;
	}

	.footer{
		display: flex;
		align-items: center;
		justify-content: center;
		padding-bottom: env(safe-area-inset-bottom);
		padding-top: 20rpx;
	}

	.footer-text{
		font-size: 22rpx;
		opacity: 0.8;
	}
</style>