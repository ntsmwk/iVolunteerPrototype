import {Component, OnInit} from '@angular/core';
import {VolunteerService} from '../../providers/volunteer.service';
import {OrganisationService} from '../../providers/organisation.service';
import {Organisation, Person, Volunteer} from '../../model/at.jku.cis';
import {Router} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Observable} from 'rxjs/Observable';
import {startWith} from 'rxjs/operators/startWith';
import {map} from 'rxjs/operators/map';


@Component({
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnInit {
  public type: PersonType;

  private volunteers: Volunteer[];
  private organisations: Organisation[];

  loginForm: FormGroup;
  filteredPersons: Observable<Person[]>;


  constructor(formBuilder: FormBuilder, private router: Router,
              private volunteerService: VolunteerService,
              private organisationService: OrganisationService) {
    this.loginForm = formBuilder.group({
      'email': new FormControl('', Validators.required)
    });
  }

  showOrganisations() {
    this.type = PersonType.ORGANISATION;
  }

  showVolunteers() {
    this.type = PersonType.VOLUNTEER;
  }

  ngOnInit() {
    this.volunteerService.getAll().toPromise().then(this.onVolunteersLoaded.bind(this));
    this.organisationService.getAll().toPromise().then(this.onOrganisationsLoaded.bind(this));
  }

  onSelectPerson() {
    if (!this.loginForm.valid) {
      return;
    }
    const email = this.loginForm.value.email;
    localStorage.setItem('person.id', email);
    if (this.type === PersonType.ORGANISATION) {
      this.router.navigateByUrl('/organisation/tasks');
    } else {
      this.router.navigateByUrl('/volunteer/tasks');
    }
  }


  private onVolunteersLoaded(volunteers: Volunteer[]) {
    this.volunteers = volunteers;
    this.onLoadingFinished();
  }

  private onOrganisationsLoaded(organisations: Organisation[]) {
    this.organisations = organisations;
    this.onLoadingFinished();
  }

  private onLoadingFinished() {
    if (this.volunteers === undefined || this.organisations === undefined) {
      return;
    }

    this.filteredPersons = this.loginForm.controls['email'].valueChanges.pipe(
      startWith(''),
      map(val => this.filter(val))
    );
  }

  private filter(email: string): Person[] {
    let persons = new Array<Person>();
    if (this.type === PersonType.ORGANISATION) {
      persons = persons.concat(this.organisations);
    } else {
      persons = persons.concat(this.volunteers);
    }
    return persons.filter((person: Person) => (person.email.toLowerCase().indexOf(email.toLowerCase()) === 0));
  }
}

enum PersonType {
  ORGANISATION,
  VOLUNTEER
}
