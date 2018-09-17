import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

@Component({
  templateUrl: './group-detail.component.html',
  styleUrls: ['./group-detail.component.scss']
})
export class FuseGroupDetailComponent implements OnInit {

  group = {
    posts: [
      {
        'user': {
          'name': 'Garry Newman',
          'avatar': 'assets/images/avatars/garry.jpg'
        },
        'message': 'Hey Guys, does somebody want to join me in the marketplace "Marketplace X"? "Marketplace Description"',
        'time': '32 minutes ago',
        'type': 'invitation',
        'like': 5,
        'share': 1
      },
      {
        'user': {
          'name': 'Garry Newman',
          'avatar': 'assets/images/avatars/garry.jpg'
        },
        'message': 'Some impressions of our last project. We are always looking for interessted and motivation people. ;)',
        'time': '3 hours ago',
        'type': 'something',
        'like': 5,
        'share': 0
      }
    ]
  };

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => this.loadGroup(params['groupId']));
  }

  private loadGroup(groupId: string) {
  }
}
