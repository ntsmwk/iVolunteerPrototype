import {Component, OnInit} from '@angular/core';

@Component({
  templateUrl: './timeline-activities.component.html',
  styleUrls: ['./timeline-activities.component.scss']
})
export class FuseTimelineActivitiesComponent implements OnInit {

  public timeline = {
    activities: [
      {
        'user': {
          'name': 'Alice Freeman',
          'avatar': 'assets/images/avatars/alice.jpg'
        },
        'message': 'finished a task',
        'time': '13 mins. ago'
      },
      {
        'user': {
          'name': 'Andrew Green',
          'avatar': 'assets/images/avatars/andrew.jpg'
        },
        'message': 'earned a new badge',
        'time': '2 hours ago'
      },
      {
        'user': {
          'name': 'Garry Newman',
          'avatar': 'assets/images/avatars/garry.jpg'
        },
        'message': 'invited you to join a project',
        'time': 'August 27, 2018'
      },
      {
        'user': {
          'name': 'Carl Henderson',
          'avatar': 'assets/images/avatars/carl.jpg'
        },
        'message': 'earned a new competence',
        'time': 'August 25, 2018'
      },
      {
        'user': {
          'name': 'Andrew Green',
          'avatar': 'assets/images/avatars/andrew.jpg'
        },
        'message': 'engaged in a new marketplace',
        'time': 'August 24, 2018'
      },
      {
        'user': {
          'name': 'Alice Freeman',
          'avatar': 'assets/images/avatars/alice.jpg'
        },
        'message': 'invited you to join a marketplace.',
        'time': 'August 20, 2018'
      }
    ]
  };

  constructor() {
  }

  ngOnInit() {

  }
}

