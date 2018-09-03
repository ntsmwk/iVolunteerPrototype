import {Component, OnInit} from '@angular/core';

import {Task} from '../_model/task';
import {Volunteer} from '../_model/volunteer';

import {LoginService} from '../_service/login.service';
import {TaskService} from '../_service/task.service';
import {Marketplace} from '../_model/marketplace';
import {isArray} from 'util';
import {CoreVolunteerService} from '../_service/core-volunteer.service';

@Component({
  selector: 'fuse-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class FuseDashboardComponent implements OnInit {

  timeline = {
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

      /*
      finished a task
	    earned a new badge
	    earned a new competence
	    engaged in a new marketplace

	    invited you to join a marketplace
	    invited you to join a project

	    challenged you (challenges, badges)

	    started following you
	    shared a post in group x
       */

    ],
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
        'share': 0,
        'media': {
          'type': 'image',
          'preview': 'assets/images/etc/tropical-beach.jpg'
        },
        'comments': [
          {
            'user': {
              'name': 'Alice Freeman',
              'avatar': 'assets/images/avatars/alice.jpg'
            },
            'time': '1 hour ago',
            'message': 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce et eleifend ligula. Fusce posuere in sapien ac facilisis. Etiam sit amet justo non felis ornare feugiat.'
          },
          {
            'user': {
              'name': 'Markus Weißenbek',
              'avatar': 'assets/images/avatars/mweissenbek.jpg'
            },
            'time': '2 minutes ago',
            'message': 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce et eleifend ligula. Fusce posuere in sapien ac facilisis. Etiam sit amet justo non felis ornare feugiat.'
          }
        ]
      },
      {
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
    ],
    tasks: []
  };

  private marketplaces: Marketplace[] = [];

  constructor(private loginService: LoginService,
              private taskService: TaskService,
              private volunteerService: CoreVolunteerService) {
  }

  ngOnInit() {
    this.timeline.tasks = new Array<Task>();
    this.loginService.getLoggedIn().toPromise().then((volunteer: Volunteer) => {
      const selected_marketplaces = JSON.parse(localStorage.getItem('marketplaces'));
      if (!isArray(selected_marketplaces)) {
        return;
      }
      this.volunteerService.findRegisteredMarketplaces(volunteer.id)
        .toPromise()
        .then((marketplaces: Marketplace[]) => {
          marketplaces
            .filter(mp => selected_marketplaces.find(selected_mp => selected_mp.id === mp.id))
            .forEach(marketplace => {
              this.marketplaces = this.marketplaces.concat(marketplace);
              this.taskService.findByParticipant(marketplace, volunteer)
                .toPromise()
                .then((tasks: Array<Task>) => this.timeline.tasks = this.timeline.tasks.concat(tasks));
            });
        });
    });
  }

  getMarketplaceName(task: Task) {
    return this.marketplaces.filter(marketplace => marketplace.id === task.marketplaceId)[0].name;
  }
}
