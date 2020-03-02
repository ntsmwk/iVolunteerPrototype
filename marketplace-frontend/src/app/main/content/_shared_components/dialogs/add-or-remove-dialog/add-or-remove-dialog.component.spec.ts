import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddOrRemoveDialogComponent } from './add-or-remove-dialog.component';

describe('AddOrRemoveDialogComponent', () => {
  let component: AddOrRemoveDialogComponent;
  let fixture: ComponentFixture<AddOrRemoveDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddOrRemoveDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddOrRemoveDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
