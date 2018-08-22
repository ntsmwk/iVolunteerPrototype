import {Component} from '@angular/core';


@Component({
  selector: 'fuse-profile-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.scss']
})
export class FuseProfileAboutComponent {

  about = {
    'general': {
      'gender': 'Female',
      'birthday': 'May 8th, 1988',
      'locations': [
        'Istanbul, Turkey',
        'New York, USA'
      ],
      'about': 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis eget pharetra felis, sed ullamcorper dui. Sed et elementum neque. Vestibulum pellente viverra ultrices. Etiam justo augue, vehicula ac gravida a, interdum sit amet nisl. Integer vitae nisi id nibh dictum mollis in vitae tortor.'
    },
    'work': {
      'occupation': 'Developer',
      'skills': 'C#, PHP, Javascript, Angular, JS, HTML, CSS',
      'jobs': [
        {
          'company': 'Self-Employed',
          'date': '2010 - Now'
        },
        {
          'company': 'Google',
          'date': '2008 - 2010'
        }
      ]
    },
    'contact': {
      'address': 'Ut pharetra luctus est quis sodales. Duis nisi tortor, bibendum eget tincidunt, aliquam ac elit. Mauris nec euismod odio.',
      'tel': ['+6 555 6600', '+9 555 5255'],
      'websites': ['withinpixels.com'],
      'emails': ['mail@withinpixels.com', 'mail@creapond.com']
    }
  };

  constructor() {
  }

}
