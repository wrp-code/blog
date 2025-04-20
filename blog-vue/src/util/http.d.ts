import { AxiosInstance } from 'axios';

declare const service: AxiosInstance;

export function get(url: string, params?: any): Promise<any>;
export function post(url: string, data?: any): Promise<any>;
export function put(url: string, data?: any): Promise<any>;
export function del(url: string, params?: any): Promise<any>;
export function uploadFile(url: string, file: File, onUploadProgress?: (progressEvent: any) => void): Promise<any>;

export default service; 