import { Component, OnInit, AfterViewInit } from '@angular/core';
import { fuseAnimations } from '../../../../../@fuse/animations';
import { BadgeGroup } from '../../_model/badge-group';
import { Badge } from '../../_model/badge';


import * as Chart from 'chart.js';

@Component({
  selector: 'fuse-encouragements',
  templateUrl: './encouragements.component.html',
  styleUrls: ['./encouragements.component.scss'],
  animations: fuseAnimations

})
export class EncouragementsComponent implements OnInit, AfterViewInit {
  badgeGroups: BadgeGroup[] = [];

  canvas: any;
  ctx: any;

  competencies = {
    'competencies': [
      { 'name': 'Paramedic', 'marketplace': 'Austrian Red Cross', 'date': '(10.08.2018)', 'task': 'Training Course: Paramedic' },
      { 'name': 'Social Skills 1', 'marketplace': 'Middle School Linz Dornach', 'date': '(18.12.2017)', 'task': 'Advent bazaar Second class' }
    ]
  };

  constructor() { }

  ngOnInit() {
    const badge1 = new Badge('Workaholic Rookie', 'assets/badges/badge 1.svg', 'Marketplace 1', 'Finish 3 tasks in one month');
    const badge2 = new Badge('Everybody\'s Darling', 'assets/badges/badge 2.svg', 'Marketplace 1', 'Receive 10 likes from individual people');
    const badge3 = new Badge('Multitasking Master', 'assets/badges/badge 3.svg', 'Marketplace 2', 'Be involved in 6 projects at a time');

    this.badgeGroups.push(new BadgeGroup('Personal Badges', 66, [badge1, badge3]));
    this.badgeGroups.push(new BadgeGroup('Social Badges', 33, [badge2]));

  }

  ngAfterViewInit() {
    this.canvas = document.getElementById('competenceRadar');
    this.ctx = this.canvas.getContext('2d');
    const myChart = new Chart(this.ctx, {
      type: 'radar',
      data: {
        labels: ['Professional skills', 'Methodical expertise', 'Social skills', 'Self competences'],
        datasets: [{
          label: 'You',
          backgroundColor: 'rgba(200,0,0,0.2)',
          data: [0.5, 0.5, 0.5, 0.5]
        }]
      },
      options: {}
    });
  }

}
