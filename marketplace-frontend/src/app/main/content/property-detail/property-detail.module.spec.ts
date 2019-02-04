import { FusePropertyDetailModule } from './property-detail.module';

describe('PropertyDetailModule', () => {
  let propertyDetailModule: FusePropertyDetailModule;

  beforeEach(() => {
    propertyDetailModule = new FusePropertyDetailModule();
  });

  it('should create an instance', () => {
    expect(propertyDetailModule).toBeTruthy();
  });
});
