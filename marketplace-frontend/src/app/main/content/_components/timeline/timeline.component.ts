import {Component, OnInit} from '@angular/core';


@Component({
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.scss']
})
export class FuseTimelineComponent implements OnInit {

  public timeline = {
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
        'share': 1,
        'comments': [
          {
            'user': {
              'name': 'Alice Freeman',
              'avatar': 'assets/images/avatars/alice.jpg'
            },
            'time': '5 minutes ago',
            'message': 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce et eleifend ligula. Fusce posuere in sapien ac facilisis. Etiam sit amet justo.'
          }
        ]
      }, {
        'user': {
          'name': 'Carl Henderson',
          'avatar': 'assets/images/avatars/carl.jpg'
        },
        'message': 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce et eleifend ligula. Fusce posuere in sapien ac facilisis. Etiam sit amet justo non felis ornare feugiat. Aenean lorem ex, ultrices sit amet ligula sed...',
        'time': 'June 10, 2015',
        'type': 'something',
        'like': 4,
        'share': 1
      },
      {
        'user': {
          'name': 'Garry Newman',
          'avatar': 'assets/images/avatars/garry.jpg'
        },
        'message': 'Yeah, finally I earned the badge I was working on!',
        'time': 'August 20, 2018',
        'type': 'badge',
        'like': 2,
        'share': 0,
        'media': {
          'type': 'image',
          'preview': 'assets/badges/badge3+text.png'
        },
      }
    ]
  };

  constructor() {
  }

  ngOnInit() {

  }
}

