import { PropertyBuildFormModule } from './property-build-form.module';

describe('PropertyBuildFormModule', () => {
  let propertyBuildFormModule: PropertyBuildFormModule;

  beforeEach(() => {
    propertyBuildFormModule = new PropertyBuildFormModule();
  });

  it('should create an instance', () => {
    expect(propertyBuildFormModule).toBeTruthy();
  });
});
