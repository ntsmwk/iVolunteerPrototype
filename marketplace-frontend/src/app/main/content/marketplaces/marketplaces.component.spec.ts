import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MarketplacesComponent } from './marketplaces.component';

describe('MarketplacesComponent', () => {
  let component: MarketplacesComponent;
  let fixture: ComponentFixture<MarketplacesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MarketplacesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MarketplacesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
