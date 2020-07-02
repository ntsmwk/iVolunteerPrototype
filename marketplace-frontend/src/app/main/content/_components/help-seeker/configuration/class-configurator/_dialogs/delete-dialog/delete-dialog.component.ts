import { OnInit, Component, Inject } from '@angular/core';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassConfiguration } from 'app/main/content/_model/meta/configurations';
import { MAT_DIALOG_DATA, MatDialogRef, MatTableDataSource } from '@angular/material';
import { ClassConfigurationService } from 'app/main/content/_service/configuration/class-configuration.service';

export class DeleteClassConfigurationDialogData {
  idsToDelete: string[];
  marketplace: Marketplace;
}

@Component({
  selector: 'delete-class-configuration-dialog',
  templateUrl: './delete-dialog.component.html',
  styleUrls: ['./delete-dialog.component.scss']
})
export class DeleteClassConfigurationDialogComponent implements OnInit {
  allClassConfigurations: ClassConfiguration[];
  loaded: boolean;

  datasource: MatTableDataSource<ClassConfiguration> = new MatTableDataSource();

  constructor(
    public dialogRef: MatDialogRef<DeleteClassConfigurationDialogData>,
    @Inject(MAT_DIALOG_DATA) public data: DeleteClassConfigurationDialogData,
    private classConfigurationService: ClassConfigurationService,
  ) { }

  ngOnInit() {
    this.data.idsToDelete = [];

    this.classConfigurationService.getAllClassConfigurations(this.data.marketplace)
      .toPromise()
      .then((classConfigurations: ClassConfiguration[]) => {
        this.allClassConfigurations = classConfigurations;
        this.datasource.data = classConfigurations;
        this.loaded = true;
      });
  }

  handleCheckboxRowClicked(row: ClassConfiguration) {
    console.log(row);
    if (this.data.idsToDelete.findIndex(id => row.id === id) === -1) {
      this.data.idsToDelete.push(row.id);
    } else {
      this.data.idsToDelete = this.data.idsToDelete.filter(id => id !== row.id);
    }
  }

  isSelected(row: ClassConfiguration) {
    return this.data.idsToDelete.findIndex(id => id === row.id) !== -1;
  }

  onSubmit() {
    this.classConfigurationService.deleteClassConfigurations(this.data.marketplace, this.data.idsToDelete).toPromise().then((ret) => {
      console.log(ret);
      this.dialogRef.close(this.data);
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.datasource.filter = filterValue.trim().toLowerCase();
  }



}
