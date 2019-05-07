import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserDefinedTaskTemplateListComponent } from './user-defined-task-template-list.component';

describe('UserDefinedTaskTemplateListComponent', () => {
  let component: UserDefinedTaskTemplateListComponent;
  let fixture: ComponentFixture<UserDefinedTaskTemplateListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserDefinedTaskTemplateListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserDefinedTaskTemplateListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
