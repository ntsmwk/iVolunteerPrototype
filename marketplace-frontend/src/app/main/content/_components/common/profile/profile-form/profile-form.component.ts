import { Component, OnInit, Input } from '@angular/core';
import {
  User,
  UserRole,
  Weekday,
  Timeslot
} from 'app/main/content/_model/user';
import { LoginService } from 'app/main/content/_service/login.service';
import { fuseAnimations } from '@fuse/animations';
import {
  FormGroup,
  FormBuilder,
  FormControl,
  Validators,
  FormArray
} from '@angular/forms';
import { CoreUserService } from 'app/main/content/_service/core-user.service';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { isNullOrUndefined } from 'util';

export interface FormTimeSlot {
  active: boolean;
  secondActive: boolean;
  weekday: Weekday;
  from1: string;
  to1: string;
  from2: string;
  to2: string;
}

@Component({
  selector: "profile-form",
  templateUrl: 'profile-form.component.html',
  styleUrls: ['profile-form.component.scss'],
  animations: fuseAnimations
})
export class ProfileFormComponent implements OnInit {
  @Input() user: User;

  globalInfo: GlobalInfo;
  currentRoles: UserRole[] = [];
  profileForm: FormGroup;
  profileFormErrors: any;

  today = new Date();
  saveSuccessful: boolean;
  loaded: boolean;

  constructor(
    private loginService: LoginService,
    private formBuilder: FormBuilder,
    private coreUserService: CoreUserService
  ) { }

  async ngOnInit() {
    this.loaded = false;

    this.globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );

    this.profileForm = this.formBuilder.group({
      formOfAddress: new FormControl('', Validators.required),
      titleBefore: new FormControl(''),
      firstname: new FormControl('', Validators.required),
      lastname: new FormControl('', Validators.required),
      titleAfter: new FormControl(''),
      nickname: new FormControl(''),

      birthday: new FormControl('', Validators.required),

      address: this.formBuilder.group({
        street: new FormControl(''),
        houseNumber: new FormControl(''),
        postcode: new FormControl(''),
        city: new FormControl(''),
        country: new FormControl('')
      }),
      about: new FormControl(''),
      phoneNumber1: new FormControl(''),
      phoneNumber2: new FormControl(''),
      phoneNumber3: new FormControl(''),
      website1: new FormControl(''),
      website2: new FormControl(''),
      website3: new FormControl(''),

      loginEmail: new FormControl('', Validators.required),
      email2: new FormControl(''),
      email3: new FormControl(''),

      timeslots: this.formBuilder.array([])
    });

    const timeslotArray = this.profileForm.controls['timeslots'] as FormArray;
    for (let i = 0; i < 7; i++) {
      timeslotArray.push(
        this.formBuilder.group({
          active: new FormControl(''),
          secondActive: new FormControl(''),
          weekday: new FormControl(''),
          from1: new FormControl(
            '',
            Validators.pattern(/^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/)
          ),
          to1: new FormControl(
            '',
            Validators.pattern(/^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/)
          ),
          from2: new FormControl(
            '',
            Validators.pattern(/^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/)
          ),
          to2: new FormControl(
            '',
            Validators.pattern(/^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/)
          )
        })
      );
    }

    this.profileForm.statusChanges.subscribe(() => {
      this.saveSuccessful = false;
    });

    this.reload();

    this.loaded = true;
  }

  private reload() {
    const timeslotsConvert: FormTimeSlot[] = [];

    for (const t of this.user.timeslots) {
      timeslotsConvert.push({
        active: t.active,
        secondActive: t.secondActive,
        weekday: t.weekday,
        from1: t.fromHours1 + ':' + (t.fromMins1 === 0 ? '00' : t.fromMins1),
        to1: t.toHours1 + ':' + (t.toMins1 === 0 ? '00' : t.toMins1),
        from2: t.fromHours2 + ':' + (t.fromMins2 === 0 ? '00' : t.fromMins2),
        to2: t.toHours2 + ':' + (t.toMins2 === 0 ? '00' : t.toMins2)
      });
    }

    this.profileForm.setValue({
      formOfAddress: this.user.formOfAddress,
      titleBefore: this.user.titleBefore,
      firstname: this.user.firstname,
      lastname: this.user.lastname,
      titleAfter: this.user.titleAfter,
      nickname: this.user.nickname,
      birthday: new Date(this.user.birthday),
      address: !isNullOrUndefined(this.user.address)
        ? this.user.address
        : this.profileForm.value.address,
      timeslots: timeslotsConvert,
      about: this.user.about,
      phoneNumber1: !isNullOrUndefined(this.user.phoneNumbers[0]) ? this.user.phoneNumbers[0] : null,
      phoneNumber2: !isNullOrUndefined(this.user.phoneNumbers[1]) ? this.user.phoneNumbers[1] : null,
      phoneNumber3: !isNullOrUndefined(this.user.phoneNumbers[2]) ? this.user.phoneNumbers[2] : null,
      website1: !isNullOrUndefined(this.user.websites[0]) ? this.user.websites[0] : null,
      website2: !isNullOrUndefined(this.user.websites[1]) ? this.user.websites[1] : null,
      website3: !isNullOrUndefined(this.user.websites[2]) ? this.user.websites[2] : null,
      loginEmail: !isNullOrUndefined(this.user.emails[0]) ? this.user.emails[0] : null,
      email2: !isNullOrUndefined(this.user.emails[1]) ? this.user.emails[1] : null,
      email3: !isNullOrUndefined(this.user.emails[2]) ? this.user.emails[2] : null,

    });

    for (let i = 0; i < 7; i++) {
      this.handleTimeslotCheckboxClicked(
        i,
        'active',
        this.user.timeslots[i].active
      );
      this.handleTimeslotCheckboxClicked(
        i,
        'secondActive',
        this.user.timeslots[i].secondActive
      );
    }
  }

  getWeekdayLabel(weekday: Weekday) {
    return Weekday.getWeekdayLabel(weekday);
  }

  async save() {
    this.user.formOfAddress = this.profileForm.value.formOfAddress;
    this.user.titleBefore = this.profileForm.value.titleBefore;
    this.user.firstname = this.profileForm.value.firstname;
    this.user.lastname = this.profileForm.value.lastname;
    this.user.titleAfter = this.profileForm.value.titleAfter;
    this.user.nickname = this.profileForm.value.nickname;
    this.user.birthday = this.profileForm.value.birthday;
    this.user.address = this.profileForm.value.address;

    this.user.about = this.profileForm.value.about;
    this.user.phoneNumbers = [];
    this.user.phoneNumbers.fill(undefined, 0, 2);
    this.user.phoneNumbers[0] = this.profileForm.value.phoneNumber1;
    this.user.phoneNumbers[1] = this.profileForm.value.phoneNumber2;
    this.user.phoneNumbers[2] = this.profileForm.value.phoneNumber3;

    this.user.websites = [];
    this.user.websites.fill(undefined, 0, 2);
    this.user.websites[0] = this.profileForm.value.website1;
    this.user.websites[1] = this.profileForm.value.website2;
    this.user.websites[2] = this.profileForm.value.website3;

    this.user.emails = [];
    this.user.emails.fill(undefined, 0, 2);
    this.user.emails[0] = this.profileForm.value.loginEmail;
    this.user.loginEmail = this.profileForm.value.loginEmail;
    this.user.emails[1] = this.profileForm.value.email2;
    this.user.emails[2] = this.profileForm.value.email3;

    this.profileForm.get('timeslots').enable();
    const formTimeslots = this.profileForm.value.timeslots as FormTimeSlot[];

    // CONVERT TIMESTAMPS
    const convertedTimeslots: Timeslot[] = [];
    for (let i = 0; i < 7; i++) {
      convertedTimeslots.push(new Timeslot(formTimeslots[i]));
    }

    this.user.timeslots = convertedTimeslots;

    try {
      await this.coreUserService.updateUser(this.user, true).toPromise();
      this.loginService.generateGlobalInfo(
        this.globalInfo.userRole,
        this.globalInfo.tenants.map((t) => t.id)
      );

      this.reload();

      this.saveSuccessful = true;
    } catch (error) {
      console.log(error);
    }


  }

  showErrorMessage(formControl: FormControl, formControlName: string) {
    return formControl.hasError('required')
      ? 'Pflichtfeld'
      : (formControlName === 'from1' ||
        formControlName === 'from2' ||
        formControlName === 'to1' ||
        formControlName === 'to2') &&
        formControl.hasError('pattern')
        ? '(H)H:MM'
        : '';
  }

  hasVolunteerRole() {
    return this.currentRoles.indexOf(UserRole.VOLUNTEER) !== -1;
  }

  handleTimeslotCheckboxClicked(
    timeslotIndex: number,
    formControlName: string,
    setTo?: boolean
  ) {
    const formArray = this.profileForm.controls['timeslots'] as FormArray;
    const timeslot = formArray.get(timeslotIndex + '');

    if (isNullOrUndefined(setTo)) {
      setTo = !timeslot.get(formControlName).value;
    }
    timeslot.get(formControlName).setValue(setTo);

    if (formControlName === 'active') {
      setTo ? timeslot.enable() : timeslot.disable();
    }

    timeslot.updateValueAndValidity();
  }
}
