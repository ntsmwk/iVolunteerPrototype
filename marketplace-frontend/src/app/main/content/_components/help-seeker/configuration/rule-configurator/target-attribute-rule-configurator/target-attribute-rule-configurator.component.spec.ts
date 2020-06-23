import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TargetAttributeRuleConfiguratorComponent } from './target-attribute-rule-configurator.component';

describe('TargetAttributeRuleConfiguratorComponent', () => {
  let component: TargetAttributeRuleConfiguratorComponent;
  let fixture: ComponentFixture<TargetAttributeRuleConfiguratorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TargetAttributeRuleConfiguratorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TargetAttributeRuleConfiguratorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
