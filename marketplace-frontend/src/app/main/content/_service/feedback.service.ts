import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Marketplace } from '../_model/marketplace';

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {

  constructor(
    private http: HttpClient
  ) { }

  getFeedbackByRecipientId(marketplace: Marketplace, recipientId: string) {
    return this.http.get(`${marketplace.url}/meta/feedback/by-recipient/${recipientId}`);
  }



}
