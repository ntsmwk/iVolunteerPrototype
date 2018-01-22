import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {DataService} from './data.service';
import {Task} from 'app/model/at.jku.cis';

@Injectable()
export class TaskService {


	private NAMESPACE = 'Task';

	constructor(private dataService: DataService<Task>) {
	};

	public getAll(): Observable<Task[]> {
		return this.dataService.getAll(this.NAMESPACE);
	}

	public getAsset(id: any): Observable<Task> {
		return this.dataService.getSingle(this.NAMESPACE, id);
	}

	public addAsset(itemToAdd: any): Observable<Task> {
		return this.dataService.add(this.NAMESPACE, itemToAdd);
	}

	public updateAsset(id: any, itemToUpdate: any): Observable<Task> {
		return this.dataService.update(this.NAMESPACE, id, itemToUpdate);
	}

	public deleteAsset(id: any): Observable<Task> {
		return this.dataService.delete(this.NAMESPACE, id);
	}

}
