import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { News } from './news';

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  constructor(private http: HttpClient) { }

  postNews(formData: FormData) {
    return firstValueFrom(this.http.post<any>('/api/postNews', formData))
  }

  getNews(duration: number) {
    const params = new HttpParams().set('duration', duration)
    return firstValueFrom(this.http.get<any>('/api/tags', { params }))
  }

  getNewsByTag(tag: string) {
    return firstValueFrom(this.http.get<News[]>(`/api/tag/${tag}`))
  }
}
