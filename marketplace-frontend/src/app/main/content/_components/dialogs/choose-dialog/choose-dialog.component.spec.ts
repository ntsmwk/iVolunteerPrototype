import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseTemplateToCopyDialogComponent } from './choose-dialog.component';

describe('ChooseDialogComponent', () => {
  let component: ChooseTemplateToCopyDialogComponent;
  let fixture: ComponentFixture<ChooseTemplateToCopyDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChooseTemplateToCopyDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChooseTemplateToCopyDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
