import { MapPropertyTestModule } from './map-property-test.module';

describe('MapPropertyTestModule', () => {
  let mapPropertyTestModule: MapPropertyTestModule;

  beforeEach(() => {
    mapPropertyTestModule = new MapPropertyTestModule();
  });

  it('should create an instance', () => {
    expect(mapPropertyTestModule).toBeTruthy();
  });
});
