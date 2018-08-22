import {Component} from '@angular/core';

@Component({
  selector: 'fuse-profile-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.scss']
})
export class FuseProfileFriendsComponent {
  friends = {
    'follower': [
      {'name': 'Carl Henderson', 'avatar': 'assets/images/avatars/carl.jpg'},
      {'name': 'Danielle Jackson', 'avatar': 'assets/images/avatars/danielle.jpg'},
      {'name': 'Garry Newman', 'avatar': 'assets/images/avatars/garry.jpg'}
    ],
    'following': [
      {'name': 'Alice Newman', 'avatar': 'assets/images/avatars/alice.jpg'},
      {'name': 'Andrew Miller', 'avatar': 'assets/images/avatars/andrew.jpg'},
      {'name': 'James Houser', 'avatar': 'assets/images/avatars/james.jpg'},
      {'name': 'Jane Doe', 'avatar': 'assets/images/avatars/jane.jpg'},
      {'name': 'Joyce Springfield', 'avatar': 'assets/images/avatars/joyce.jpg'},
      {'name': 'Katherine Green', 'avatar': 'assets/images/avatars/katherine.jpg'},
      {'name': 'Vincent Killdow', 'avatar': 'assets/images/avatars/vincent.jpg'}
    ]
  };

}
