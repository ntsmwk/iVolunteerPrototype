import {Component, OnInit, ViewChild} from '@angular/core';
import { MatTableDataSource } from '@angular/material';


// const COMP_DATA = [
//   {label: 'Project Management', issuer: 'ÖRK', date1: '12.04.2019', date2: '12.04.2019', status: 'check'},
//   {label: 'Communication Skills', issuer: 'FF Altenberg', date1: '15.02.2017', date2: '15.02.2017', status: 'check'},
//   {label: 'Teamwork', issuer: 'ÖRK', date1: '10.01.2018', date2: '10.01.2018', status: 'check'},
//   {label: 'Diligence', issuer: 'ÖRK', date1: '10.01.2018', date2: '10.01.2018',  status: 'check'},
//   {label: 'Firetruck Driver', issuer: 'FF Altenberg', date1: '15.02.2017', date2: '15.02.2018',  status: 'access_time'},

// ];

// const FEEDBACK_DATA = [
//   {label: 'Yearly Feedback:', issuer: 'ÖRK', date1: '31.12.2018', date2: '-', status: 'check'},

// ]


const COMP_DATA = [
  // {label: 'Leadership Skills', issuer: 'FF Altenberg', date1: '14.06.2019', date2: '14.06.2019', status: 'new'},
  {label: 'Project Management', issuer: 'ÖRK', date1: '12.04.2019', date2: '12.04.2019', status: 'check'},
  {label: 'Communication Skills', issuer: 'FF Altenberg', date1: '15.02.2017', date2: '15.02.2017', status: 'check'},
  {label: 'Teamwork', issuer: 'ÖRK', date1: '10.01.2018', date2: '10.01.2018', status: 'check'},
  {label: 'Diligence', issuer: 'ÖRK', date1: '10.01.2018', date2: '10.01.2018',  status: 'check'},
  {label: 'Firetruck Driver', issuer: 'FF Altenberg', date1: '15.02.2017', date2: '14.05.2018',  status: 'access_time'},

];

const COMP_DATA_NEW = [
  {label: 'Leadership Skills', issuer: 'FF Altenberg', date1: '14.06.2019', date2: '-', status: 'clear'},
  {label: 'Firetruck Driver', issuer: 'FF Altenberg', date1: '14.05.2019', date2: '-',  status: 'clear'},
  {label: 'Project Management', issuer: 'ÖRK', date1: '12.04.2019', date2: '-', status: 'clear'},

  {label: 'Communication Skills', issuer: 'FF Altenberg', date1: '15.02.2017', date2: '-', status: 'clear'},
  {label: 'Teamwork', issuer: 'ÖRK', date1: '10.01.2018', date2: '-', status: 'clear'},
  {label: 'Diligence', issuer: 'ÖRK', date1: '10.01.2018', date2: '-',  status: 'clear'},

];

const COMP_DATA_VERIFIED = [
  {label: 'Leadership Skills', issuer: 'FF Altenberg', date1: '10.06.2019', date2: '19.06.2019', status: 'check'},

  {label: 'Firetruck Driver', issuer: 'FF Altenberg', date1: '14.05.2019', date2: '19.06.2019',  status: 'check'},
  {label: 'Project Management', issuer: 'ÖRK', date1: '12.04.2019', date2: '19.06.2019', status: 'check'},

  {label: 'Communication Skills', issuer: 'FF Altenberg', date1: '15.02.2017', date2: '19.06.2019', status: 'check'},
  {label: 'Teamwork', issuer: 'ÖRK', date1: '10.01.2018', date2: '19.06.2019', status: 'check'},
  {label: 'Diligence', issuer: 'ÖRK', date1: '10.01.2018', date2: '19.06.2019',  status: 'check'},

];

const FEEDBACK_DATA = [
  {label: 'Yearly Feedback:', issuer: 'ÖRK', date1: '31.12.2018', date2: '-', status: 'check', type: 'star'},


];

const FEEDBACK_DATA_NEW = [
  {label: 'Lead Firebrigade Operation:', issuer: 'FF Altenberg', date1: '14.06.2019', date2: '-', status: 'clear', type: 'star'},
  { label: 'Check Tire Pressure', issuer: 'FF Altenberg', date1: '19.06.2019', date2: '-', status: 'clear', type: 'kudos' },
  { label: 'Medical Care Transport:', issuer: 'FF Altenberg', date1: '19.06.2019', date2: '-', status: 'clear', type: 'star' },
  {label: 'Firetruck Driver Renewed:', issuer: 'FF Altenberg', date1: '12.04.2019', date2: '-', status: 'clear', type: 'kudos'},
  {label: 'Yearly Feedback:', issuer: 'ÖRK', date1: '31.12.2018', date2: '-', status: 'clear', type: 'star'},



];

const FEEDBACK_DATA_VERIFIED = [
  {label: 'Lead Firebrigade Operation:', issuer: 'FF Altenberg', date1: '14.06.2019', date2: '19.06.2019', status: 'check', type: 'star'},
  { label: 'Check Tire Pressure', issuer: 'FF Altenberg', date1: '19.06.2019', date2: '19.06.2019', status: 'check', type: 'kudos' },
  { label: 'Medical Care Transport:', issuer: 'FF Altenberg', date1: '19.06.2019', date2: '19.06.2019', status: 'check', type: 'star' },
  {label: 'Firetruck Driver Renewed:', issuer: 'FF Altenberg', date1: '12.04.2019', date2: '19.06.2019', status: 'check', type: 'kudos'},
  {label: 'Yearly Feedback:', issuer: 'ÖRK', date1: '31.12.2018', date2: '19.06.2019', status: 'check', type: 'start'},



];


@Component({
  selector: 'recruit-view',
  templateUrl: './recruit-view.component.html',
  styleUrls: ['./recruit-view.component.scss'],
  host: {
    '(document:keypress)': 'handleKeyboardEvent($event)'
  }
})
export class RecruitViewComponent implements OnInit {

  dataSourceComp = new MatTableDataSource<any>();
  dataSourceFeedback = new MatTableDataSource<any>();
  displayedColumns = ['label', 'issuer', 'date1', 'evidence', 'details', 'date2', 'status'];

  duringVerify: boolean;
  verifyStage: number;

  ngOnInit() {
   
    this.dataSourceComp.data = COMP_DATA_NEW;
    this.dataSourceFeedback.data = FEEDBACK_DATA_NEW;

    this.duringVerify = false;
this.verifyStage = 1;
    console.log("config page");
  }

  handleKeyboardEvent(event: KeyboardEvent) {


    if (event.key == '1') {
      this.dataSourceComp.data = COMP_DATA;
      this.dataSourceFeedback.data = FEEDBACK_DATA;
      this.verifyStage = 0;

    }

    if (event.key == '2') {
      this.dataSourceComp.data = COMP_DATA_NEW;
      this.dataSourceFeedback.data = FEEDBACK_DATA_NEW;
      this.verifyStage = 1;

    }

    if (event.key == '3') {
      this.dataSourceComp.data = COMP_DATA_VERIFIED;
      this.dataSourceFeedback.data = FEEDBACK_DATA_VERIFIED;
      this.verifyStage = 2;

    }

    if (event.key == '4') {
   
    }


    
  }

  pressedVerifyAll() {
    this.duringVerify = true;

    for (let e of this.dataSourceComp.data) {
      e.status = 'verifying'
    }

    for (let e of this.dataSourceFeedback.data) {
      e.status = 'verifying'
    }



    setTimeout( () => {

      this.dataSourceComp.data[2].status = 'check';
      this.dataSourceComp.data[3].status = 'check';
      this.dataSourceComp.data[4].status = 'check';

      this.dataSourceComp.data[2].date2 = '19.06.2019';
      this.dataSourceComp.data[3].date2 = '19.06.2019';
      this.dataSourceComp.data[4].date2 = '19.06.2019';

      
      // this.dataSourceComp.data[0] = COMP_DATA_VERIFIED[0];
    this.dataSourceFeedback.data[2].status = 'check';
    this.dataSourceFeedback.data[4].status = 'check';

    this.dataSourceFeedback.data[2].date2 = '19.06.2019';
    this.dataSourceFeedback.data[4].date2 = '19.06.2019';


    }, 1500)

    setTimeout( () => {

      this.dataSourceComp.data[0].status = 'check';
      this.dataSourceComp.data[5].status = 'check';

      this.dataSourceComp.data[0].date2 = '19.06.2019';
      this.dataSourceComp.data[5].date2 = '19.06.2019';

      
      // this.dataSourceComp.data[0] = COMP_DATA_VERIFIED[0];
    this.dataSourceFeedback.data[1].status = 'check';

    this.dataSourceFeedback.data[1].date2 = '19.06.2019';



    }, 3500)

    setTimeout( () => {

      this.dataSourceComp.data[1].status = 'check';

      this.dataSourceComp.data[1].date2 = '19.06.2019';

      
      // this.dataSourceComp.data[0] = COMP_DATA_VERIFIED[0];
    this.dataSourceFeedback.data[0].status = 'check';
    this.dataSourceFeedback.data[3].status = 'check';

    this.dataSourceFeedback.data[0].date2 = '19.06.2019';
    this.dataSourceFeedback.data[3].date2 = '19.06.2019';


    this.verifyStage = 2;


    }, 4000)

    
  }


}
