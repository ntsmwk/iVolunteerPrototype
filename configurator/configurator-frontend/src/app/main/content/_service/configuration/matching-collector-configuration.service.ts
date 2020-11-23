import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatchingConfiguration } from '../../_model/configurator/configurations';
import { environment } from "environments/environment";

@Injectable({
    providedIn: 'root'
})

export class MatchingEntityDataService {
    constructor(
        private http: HttpClient
    ) { }

    getMatchingData(matchingConfiguration: MatchingConfiguration) {
        return this.http.put(`${environment.CONFIGURATOR_URL}/matching-entity-data/`, matchingConfiguration);
    }
}
