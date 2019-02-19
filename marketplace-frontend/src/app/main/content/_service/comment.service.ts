import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Marketplace} from '../_model/marketplace';

import {isNullOrUndefined} from 'util';

import {Comment} from '../_model/comment';

@Injectable({
  providedIn: 'root'
})

export class CommentService{

  constructor(private http: HttpClient) {
  }


  findall(){
  	return this.http.get(`/comment`);
  }

  save(marketplace: Marketplace, comment: Comment){
    let comment1: Comment = {text: '123'};
    console.log(comment1.text);
       	return this.http.post('http://localhost:4200/marketplace/comment', comment1);
  }
}
