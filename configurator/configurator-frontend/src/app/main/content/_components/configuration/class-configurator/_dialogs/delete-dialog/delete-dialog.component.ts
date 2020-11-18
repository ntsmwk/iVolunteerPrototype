import { OnInit, Component, Inject } from '@angular/core';
import { ClassConfiguration } from 'app/main/content/_model/configurator/configurations';
import { MAT_DIALOG_DATA, MatDialogRef, MatTableDataSource } from '@angular/material';
import { ClassConfigurationService } from 'app/main/content/_service/configuration/class-configuration.service';
import { isNullOrUndefined } from 'util';
import { ResponseService } from 'app/main/content/_service/response.service';

export class DeleteClassConfigurationDialogData {
  idsToDelete: string[];
  tenantId: string;
  redirectUrl: string;
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
  currentSortKey: 'name' | 'date';
  currentSortType: 'az' | 'za' = 'az';

  currentFilter: string;


  constructor(
    public dialogRef: MatDialogRef<DeleteClassConfigurationDialogData>,
    @Inject(MAT_DIALOG_DATA) public data: DeleteClassConfigurationDialogData,
    private classConfigurationService: ClassConfigurationService,
    private responseService: ResponseService
  ) { }

  async ngOnInit() {

    this.data.idsToDelete = [];

    this.classConfigurationService.getAllClassConfigurationsByTenantId(this.data.tenantId)
      .toPromise()
      .then((classConfigurations: ClassConfiguration[]) => {
        this.allClassConfigurations = classConfigurations;
        this.datasource.data = classConfigurations;
        this.sortClicked('date');
        this.loaded = true;
      });
  }

  handleCheckboxRowClicked(row: ClassConfiguration) {
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
    this.responseService.sendClassConfiguratorResponse(this.data.redirectUrl, null, this.data.idsToDelete, "delete").toPromise().then(() => {
      this.classConfigurationService.deleteClassConfigurations(this.data.idsToDelete).toPromise().then((ret) => {
        this.dialogRef.close(this.data);
      });
    }).catch(error => {
      console.log("error - rollback");
      console.log(error);
    });
  }

  applyFilter(event: Event) {
    this.currentFilter = (event.target as HTMLInputElement).value;
    this.datasource.filter = this.currentFilter.trim().toLowerCase();
  }

  sortClicked(sortKey: 'name' | 'date') {
    if (this.currentSortKey === sortKey) {
      this.switchSortType();
    } else {
      this.currentSortType = 'az';
    }

    if (sortKey === 'date') {
      this.datasource.data = this.allClassConfigurations.sort((a, b) => b.timestamp.valueOf() - a.timestamp.valueOf())
    }
    if (sortKey === 'name') {
      this.datasource.data = this.allClassConfigurations.sort((a, b) => b.name.trim().localeCompare(a.name.trim()));
    }
    if (this.currentSortType === 'za') {
      this.datasource.data.reverse();
    }
    this.currentSortKey = sortKey;

    if (!isNullOrUndefined(this.currentFilter)) {
      this.datasource.filter = this.currentFilter.trim().toLowerCase();
    }

  }

  switchSortType() {
    this.currentSortType === 'az' ? this.currentSortType = 'za' : this.currentSortType = 'az';
  }



}
