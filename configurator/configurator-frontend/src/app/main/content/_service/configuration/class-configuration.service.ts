import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ClassConfiguration } from '../../_model/configurator/configurations';
import { environment } from 'environments/environment';
import { ConfirmClassConfigurationSaveDialogData } from '../../_components/configuration/class-configurator/_dialogs/confirm-save-dialog/confirm-save-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class ClassConfigurationService {

  constructor(
    private http: HttpClient
  ) { }

  getAllClassConfigurations() {
    return this.http.get(`${environment.CONFIGURATOR_URL}/class-configuration/all`);
  }

  getAllClassConfigurationsByTenantId(tenantId: string) {
    return this.http.get(`${environment.CONFIGURATOR_URL}/class-configuration/all/tenant/${tenantId}`);
  }

  getAllClassConfigurationById(id: string) {
    return this.http.get(`${environment.CONFIGURATOR_URL}/class-configuration/${id}`);
  }

  getClassConfigurationByName(name: string) {
    return this.http.get(`${environment.CONFIGURATOR_URL}/class-configuration/by-name/${name}`);
  }

  getAllForClassConfigurationInOne(id: string) {
    return this.http.get(`${environment.CONFIGURATOR_URL}/class-configuration/all-in-one/${id}`);
  }

  createNewEmptyClassConfiguration(tenantId: string, name: string, description: string) {
    return this.http.post(`${environment.CONFIGURATOR_URL}/class-configuration/new-empty`, [tenantId, name, description]);
  }

  createNewClassConfiguration(tenantId: string, name: string, description: string, ) {
    return this.http.post(`${environment.CONFIGURATOR_URL}/class-configuration/new`, [tenantId, name, description]);
  }

  saveClassConfiguration(classConfiguration: ClassConfiguration) {
    return this.http.put(`${environment.CONFIGURATOR_URL}/class-configuration/save`, classConfiguration);
  }

  saveFullClassConfiguration(req: ConfirmClassConfigurationSaveDialogData) {
    return this.http.put(`${environment.CONFIGURATOR_URL}/class-configuration/save-all-in-one`, req);
  }


  saveClassConfigurationMeta(id: string, name: string, description: string) {
    return this.http.put(`${environment.CONFIGURATOR_URL}/class-configuration/${id}/save-meta/`, [name, description]);
  }

  deleteClassConfiguration(id: string) {
    return this.http.delete(`${environment.CONFIGURATOR_URL}/class-configuration/${id}/delete`);
  }

  deleteClassConfigurations(ids: string[]) {
    return this.http.put(`${environment.CONFIGURATOR_URL}/class-configuration/delete-multiple`, ids);
  }



}
