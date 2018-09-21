import { Component, OnInit } from '@angular/core';
import {fuseAnimations} from '../../../../../@fuse/animations';

@Component({
  selector: 'fuse-encouragements',
  templateUrl: './encouragements.component.html',
  styleUrls: ['./encouragements.component.scss'],
  animations: fuseAnimations

})
export class EncouragementsComponent implements OnInit {
  badges = {
    'badges': [
      {'name': 'Workaholic Rookie', 'image': 'assets/badges/badge 1.svg', 'source': 'Marketplace 1', 'description': 'Finish 3 tasks in one month'},
      {'name': 'Everybody\'s Darling', 'image': 'assets/badges/badge 2.svg', 'source': 'Marketplace 1', 'description': 'Receive 10 likes from individual people'},
      {'name': 'Multitasking Master', 'image': 'assets/badges/badge 3.svg', 'source': 'Marketplace 2', 'description': 'Be involved in 6 projects at a time'}
    ]
  };

  competencies = {
    'competencies': [
      {'name': 'Paramedic', 'marketplace': 'Austrian Red Cross', 'date': '(10.08.2018)', 'task': 'Training Course: Paramedic'},
      {'name': 'Social Skills 1', 'marketplace': 'Middle School Linz Dornach', 'date': '(18.12.2017)', 'task': 'Advent bazaar Second class'}
    ]
  };

  constructor() { }

  ngOnInit() {
  }

}
