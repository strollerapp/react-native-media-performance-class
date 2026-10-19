import { TurboModuleRegistry, type TurboModule } from 'react-native';

export interface Spec extends TurboModule {
  getMediaPerformanceClass(): Promise<number>;
}

export default TurboModuleRegistry.get<Spec>('MediaPerformanceClass');
