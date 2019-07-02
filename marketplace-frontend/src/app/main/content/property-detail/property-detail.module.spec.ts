import { PropertyDetailModule } from './property-detail.module';

describe('PropertyDetailModule', () => {
  let propertyDetailModule: PropertyDetailModule;

  beforeEach(() => {
    propertyDetailModule = new PropertyDetailModule();
  });

  it('should create an instance', () => {
    expect(propertyDetailModule).toBeTruthy();
  });
});
