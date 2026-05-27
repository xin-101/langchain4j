const ENV = process.env.NODE_ENV || 'development';

const API_CONFIG = {
    development: {
        baseURL: 'http://localhost:8080',  // 本地开发环境地址
        timeout: 5000,
    },
    production: {
        baseURL: 'https://api.your-domain.com/api',
        timeout: 10000,
    }
};

const currentConfig = API_CONFIG[ENV];

export const apiConfig = {
    baseURL: currentConfig.baseURL,
    timeout: currentConfig.timeout,
    headers: {
        'Content-Type': 'application/json',
    }
};

export const getApiUrl = (path) => {
    return `${apiConfig.baseURL}${path}`;
};