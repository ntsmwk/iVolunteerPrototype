import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ShareDialog } from './share-dialog/share-dialog.component';


const DATA = [
  { engAsset: 'Project Management', assetType: 'Competence', issuer: 'ÖRK', date: '-', status: 'close' },
  { engAsset: 'Yearly Feedback', assetType: 'Feedback', issuer: 'ÖRK', date: '3.12.2018', status: 'check' },
  { engAsset: 'Diligence', assetType: 'Competence', issuer: 'ÖRK', date: '1.7.2018', status: 'check' },
  { engAsset: 'Communication Skills', assetType: 'Competence', issuer: 'ÖRK', date: '1.1.2018', status: 'check' },
  { engAsset: 'Teamwork', assetType: 'Competence', issuer: 'ÖRK', date: '1.1.2018', status: 'check' },
];

const DATA_TASKS = [
  { label: 'Medical Care Transport', issuer: 'ÖRK', date1: '10.06.2019', date2: '2:00 hrs', status: 'none' },
  { label: 'Donation Collection', issuer: 'ÖRK', date1: '05.06.2019', date2: '1:30 hrs', status: 'none' },
  { label: 'Shopping Elementaries', issuer: 'HelpYourNeighbor', date1: '30.05.2019', date2: '30 mins', status: 'none' },
  { label: 'Shopping Elementaries', issuer: 'HelpYourNeighbor', date1: '28.05.2019', date2: '35 mins', status: 'none' },
  { label: 'Equipment Service', issuer: 'FF Altenberg', date1: '15.05.2019', date2: '1:20 hrs', status: 'none' },
  { label: 'Shopping Elementaries', issuer: 'HelpYourNeighbor', date1: '14.05.2019', date2: '25 mins', status: 'none' },
];

const COMP_DATA = [
  // {label: 'Leadership Skills', issuer: 'FF Altenberg', date1: '14.06.2019', date2: '14.06.2019', status: 'new'},
  { label: 'Firetruck Driver', issuer: 'FF Altenberg', date1: '14.05.2019', date2: '14.05.2019', status: 'none' },
  { label: 'Project Management', issuer: 'ÖRK', date1: '12.04.2019', date2: '12.04.2019', status: 'none' },
  { label: 'Communication Skills', issuer: 'FF Altenberg', date1: '15.02.2017', date2: '15.02.2017', status: 'none' },
  { label: 'Teamwork', issuer: 'ÖRK', date1: '10.01.2018', date2: '10.01.2018', status: 'none' },
  { label: 'Diligence', issuer: 'ÖRK', date1: '10.01.2018', date2: '10.01.2018', status: 'none' },

];

const FEEDBACK_DATA = [
  { label: 'Firetruck Driver Renewed:', issuer: 'FF Altenberg', date1: '12.04.2019', date2: '-', status: 'none', type: 'kudos' },
  { label: 'Yearly Feedback:', issuer: 'ÖRK', date1: '31.12.2018', date2: '-', status: 'none', type: 'star' },
];

@Component({
  selector: 'dashboard-volunteer',
  templateUrl: './dashboard-volunteer.component.html',
  styleUrls: ['./dashboard-volunteer.component.scss'],
})
export class DashboardVolunteerComponent implements OnInit {
  widget0: any;
  widget1: any;
  widget2: any;
  widget3: any;

  dataSourceComp = new MatTableDataSource<any>();
  dataSourceFeedback = new MatTableDataSource<any>();
  dataSourceTasks = new MatTableDataSource<any>();

  displayedColumnsCompetence = ['issuer', 'label_competence', 'date1'/*, 'evidence', 'date2', 'status'*/, 'details'];
  displayedColumnsFeedback = ['issuer', 'label_feedback', 'date1'/*, 'evidence', 'date2', 'status'*/, 'details'];
  displayedColumnsTasks = ['issuer', 'label_tasks', 'date1'/*, 'evidence', 'date2', 'status'*/, 'details'];


  constructor(public dialog: MatDialog) {

  }

  ngOnInit() {


    this.dataSourceTasks.data = DATA_TASKS;
    this.dataSourceFeedback.data = FEEDBACK_DATA;
    this.dataSourceComp.data = COMP_DATA;

  }

  test(event: any) {
    console.log(event);
  }


  triggerShareDialog() {
    const dialogRef = this.dialog.open(ShareDialog, {
      width: '700px',
      height: '255px',
      data: { name: "share" }
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      console.log('The dialog was closed');
      console.log("done")
    });
  }

  triggerStoreDialog() {

    const dialogRef = this.dialog.open(ShareDialog, {
      width: '700px',
      height: '255px',
      data: { name: "store" }
    });
  

    dialogRef.afterClosed().subscribe((result: any) => {
      console.log('The dialog was closed');
      this.toggleShareInRep();
      console.log("done")
    });
  }


  toggleShareInRep() {
    
  }



}

export interface DialogData {
  name: string;
}

