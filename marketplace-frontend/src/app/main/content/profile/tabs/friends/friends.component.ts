import {Component} from '@angular/core';

@Component({
  selector: 'fuse-profile-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.scss']
})
export class FuseProfileFriendsComponent {
  friends = [
    {'name': 'Garry Newman', 'avatar': 'assets/images/avatars/garry.jpg'},
    {'name': 'Carl Henderson', 'avatar': 'assets/images/avatars/carl.jpg'}
  ];

}
