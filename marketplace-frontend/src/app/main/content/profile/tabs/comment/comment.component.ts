import {Component, OnInit } from '@angular/core';
import {Comment} from '../../../_model/comment';
import {CommentService} from '../../../_service/comment.service';
import { Marketplace } from '../../../_model/marketplace';

@Component({
  selector: 'fuse-profile-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.scss']
})

export class FuseProfileCommentComponent implements OnInit {

  comment: Comment = {
 	text: 'This could be your text'
  };



  constructor(private commentService: CommentService) {

  	}

  ngOnInit() {
   	this.commentService.findall();
  }




  public save(marketplace: Marketplace, comment: Comment){
  	console.log(this.commentService.save(marketplace, comment));
  }
}
