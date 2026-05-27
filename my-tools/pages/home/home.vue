<template>
	<view class="container">
		<!--用户信息头部-->
		<view class="user-header">
			<view class="user-info">
				<view class="avatar"></view>
				<view class="user-details">
					<text class="username">{{userId}}</text>
					<text class="welcome-text">欢迎使用智能助手</text>
				</view>
			</view>
			<view class="settings-btn" @click="goToSettings">
				<text class="settings-icon">⚙</text>
			</view>
		</view>
		
		<!--功能模块-->
		<view class="modules-section">
			<text class="section-title">智能助手服务</text>
			
			<view class="modules-grid">
				<!--失物找领助手-->
				<view class="modules-card" @click="goToLostFound">
					<view class="modules-icon lost-found">
						<text class="icon-text">🔍</text>
					</view>
					<view class="modules-info">
						<text class="modules-title">失物找领助手</text>
						<text class="modules-desc">帮您快速找回丢失物品</text>
					</view>
				</view>
				
				<!--航空票务助手-->
				<view class="modules-card" @click="goToAirTicket">
					<view class="modules-icon air-ticket">
						<text class="icon-text">✈</text>
					</view>
					<view class="modules-info">
						<text class="modules-title">航空票务助手</text>
						<text class="modules-desc">便捷的机票查询与预订</text>
					</view>
				</view>
				
				<!--旅游助手-->
				<view class="modules-card" @click="goToTravel">
					<view class="modules-icon travel-assistant">
						<text class="icon-text">📖</text>
					</view>
					<view class="modules-info">
						<text class="modules-title">旅游助手</text>
						<text class="modules-desc">个性化旅游规划建议</text>
					</view>
				</view>
			</view>
		</view>
		
		<!--快捷功能-->
		<view class="quick-section">
			<text class="quick-title">快捷功能</text>
			<view class="quick-grid">
				<view class="quick-card" @click="goToRecords">
					<view class="quick-icon records-icon">📋</view>
					<text class="quick-text">使用记录</text>
				</view>
				<view class="quick-card" @click="goToHelp">
					<view class="quick-icon help-icon">❓</view>
					<text class="quick-text">帮助中心</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				userId: ''
			};
		},
		onLoad() {
			this.userId = uni.getStorageSync('userId');
		},
		methods: {
			goToSettings() {
				uni.navigateTo({
					url: '/pages/setup/setup'
				});
			},
			goToLostFound() {
				uni.navigateTo({
					url: '/pages/lost-found/lost-found'
				});
			},
			goToAirTicket() {
				uni.showToast({
					title: '功能开发中',
					icon: 'none'
				});
			},
			goToTravel() {
				uni.showToast({
					title: '功能开发中',
					icon: 'none'
				});
			},
			goToRecords() {
				uni.showToast({
					title: '功能开发中',
					icon: 'none'
				});
			},
			goToHelp() {
				uni.showToast({
					title: '功能开发中',
					icon: 'none'
				});
			}
		}
	}
</script>

<style>
.container{
	min-height: 100vh;
	background: #f5f7fb;
	padding: 20rpx 24rpx 40rpx;
	box-sizing: border-box;
}

.user-header{
	margin-top: 20rpx;
	padding: 28rpx;
	border-radius: 20rpx;
	background: linear-gradient(135deg,#4f9cfb 0%,#6d77ff 100%);
	color: #ffffff;
	display: flex;
	align-items: center;
	justify-content: space-between;
	box-shadow: 0 12rpx 28rpx rgba(0,0,0,0.18);
}

.user-info{
	display: flex;
	align-items: center;
	gap: 20rpx;
}

.avatar{
	width: 96rpx;
	height: 96rpx;
	border-radius: 50%;
	background: rgba(255,255,255,0.3);
	box-shadow: 0 10rpx 20rpx rgba(0,0,0,0.2);
}

.user-details{
	display: flex;
	flex-direction: column;
}

.username{
	font-size: 40rpx;
	font-weight: 700;
	letter-spacing: 1rpx;
}

.welcome-text{
	font-size: 26rpx;
	opacity: 0.9;
	margin-top: 8rpx;
}

.settings-btn{
	width: 72rpx;
	height: 72rpx;
	border-radius: 50%;
	background: rgba(255,255,255,0.25);
	display: flex;
	align-items: center;
	justify-content: center;
	transition: transform 0.12s ease, background 0.12s ease;
}

.settings-btn:active{
	transform: scale(0.96);
	background: rgba(255,255,255,0.35);
}

.settings-icon{
	color: #ffffff;
	font-size: 36rpx;
	font-weight: 600;
}

.modules-section{
	margin-top: 34rpx;
}

.section-title{
	font-size: 32rpx;
	font-weight: 700;
	color: #333333;
	padding: 0 6rpx;
	margin-bottom: 20rpx;
}

.modules-grid{
	display: flex;
	flex-direction: column;
	gap: 24rpx;
}

.modules-card{
	background: #ffffff;
	border-radius: 16rpx;
	padding: 24rpx;
	display: flex;
	align-items: center;
	gap: 24rpx;
	box-shadow: 0 10rpx 24rpx rgba(0,0,0,0.08);
	border: 1rpx solid rgba(0,0,0,0.04);
	transition: transform 0.12s ease, box-shadow 0.12s ease;
}

.modules-card:active{
	transform: scale(0.98);
	box-shadow: 0 6rpx 16rpx rgba(0,0,0,0.08);
}

.modules-icon{
	width: 64rpx;
	height: 64rpx;
	border-radius: 12rpx;
	box-shadow: 0 8rpx 16rpx rgba(0,0,0,0.12);
	display: flex;
	align-items: center;
	justify-content: center;
	flex-shrink: 0;
}

.icon-text{
	font-size: 36rpx;
}

.modules-icon.lost-found{
	background: linear-gradient(135deg,#ff6b6b 0%,#ff8e8e 100%);
}

.modules-icon.air-ticket{
	background: linear-gradient(135deg,#56ccf2 0%,#2f80ed 100%);
}

.modules-icon.travel-assistant{
	background: linear-gradient(135deg,#9be15d 0%,#00e3ae 100%);
}

.modules-info{
	display: flex;
	flex-direction: column;
	flex: 1;
	gap: 8rpx;
}

.modules-title{
	font-size: 32rpx;
	font-weight: 700;
	color: #333333;
}

.modules-desc{
	font-size: 24rpx;
	color: #999999;
}

.modules-card::after{
	content: '';
	margin-left: auto;
	width: 0;
	height: 0;
	border-top: 12rpx solid transparent;
	border-bottom: 12rpx solid transparent;
	border-left: 12rpx solid #c0c4cc;
	opacity: 0.9;
}

.quick-section{
	margin-top: 34rpx;
}

.quick-title{
	font-size: 32rpx;
	font-weight: 700;
	color: #333333;
	padding: 0 6rpx;
	margin-bottom: 20rpx;
}

.quick-grid{
	display: grid;
	grid-template-columns: 1fr 1fr;
	gap: 24rpx;
}

.quick-card{
	background: #ffffff;
	border-radius: 16rpx;
	padding: 36rpx 24rpx;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	gap: 16rpx;
	box-shadow: 0 10rpx 24rpx rgba(0,0,0,0.08);
	border: 1rpx solid rgba(0,0,0,0.04);
	transition: transform 0.12s ease, box-shadow 0.12s ease;
}

.quick-card:active{
	transform: scale(0.98);
	box-shadow: 0 6rpx 16rpx rgba(0,0,0,0.08);
}

.quick-icon{
	font-size: 48rpx;
}

.quick-text{
	font-size: 28rpx;
	color: #333333;
	font-weight: 500;
}

.container{
	padding-bottom: calc(40rpx + env(safe-area-inset-bottom));
}
</style>
