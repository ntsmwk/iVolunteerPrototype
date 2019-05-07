import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogFactoryComponent } from './dialog-factory.component';

describe('DialogFactoryComponent', () => {
  let component: DialogFactoryComponent;
  let fixture: ComponentFixture<DialogFactoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogFactoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogFactoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
