import { Injectable } from '@angular/core';
import axios from 'axios';

type AxiosRequestConfig = axios.AxiosRequestConfig;
type AxiosResponse = axios.AxiosResponse;

@Injectable({
  providedIn: 'root'
})
export class AxiosService {

  // 定义 axios 实例，避免修改全局 axios 配置（Angular 最佳实践）
  private axiosInstance;

  constructor() { 
    // 创建独立的 axios 实例，而非修改全局配置
    this.axiosInstance = axios.create({
      baseURL: 'http://localhost:8080',
      headers: {
        'Content-Type': 'application/json'
      }
    });
  }

  /**
   * 通用请求方法，返回标准的 Promise 类型
   * @param method HTTP 请求方法 (get/post/put/delete 等)
   * @param url 请求地址（相对于 baseURL）
   * @param data 请求体数据（post/put 等方法使用）
   * @returns 标准 Promise，包含 AxiosResponse 响应对象
   */
  request(method: string, url: string, data: any): Promise<AxiosResponse<any>> {
    // 构建请求配置
    const config: AxiosRequestConfig = {
      method: method,
      url: url,
      // 根据请求方法区分参数位置（get 用 params，post/put 用 data）
      ...(method.toLowerCase() === 'get' ? { params: data } : { data: data })
    };

    // 显式转换为标准 Promise，解决类型不兼容问题
    return Promise.resolve(this.axiosInstance.request(config));
  }

  // 可选：封装常用的 GET/POST 方法，简化调用
  get<T = any>(url: string, params?: any): Promise<AxiosResponse<T>> {
    return Promise.resolve(this.axiosInstance.get(url, { params }));
  }

  post<T = any>(url: string, data?: any): Promise<AxiosResponse<T>> {
    return Promise.resolve(this.axiosInstance.post(url, data));
  }
}