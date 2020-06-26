import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestRuleConfigurationComponent } from './test-rule-configuration.component';

describe('TestRuleConfigurationComponent', () => {
  let component: TestRuleConfigurationComponent;
  let fixture: ComponentFixture<TestRuleConfigurationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestRuleConfigurationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestRuleConfigurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
