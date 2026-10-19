import { useEffect, useState } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { getMediaPerformanceClass } from '@strollerapp/react-native-media-performance-class';

const ANDROID_12 = 31;

function describeClass(value: number) {
  if (value >= ANDROID_12) {
    return 'High-performance media device';
  }

  return 'No class declared or estimated';
}

export default function App() {
  const [performanceClass, setPerformanceClass] = useState<number | null>(null);

  useEffect(() => {
    getMediaPerformanceClass().then(setPerformanceClass);
  }, []);

  return (
    <View style={styles.container}>
      <Text style={styles.label}>Media Performance Class</Text>
      <Text style={styles.value}>
        {performanceClass == null ? 'Loading…' : String(performanceClass)}
      </Text>
      {performanceClass == null ? null : (
        <Text style={styles.summary}>{describeClass(performanceClass)}</Text>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    paddingHorizontal: 24,
    gap: 8,
  },
  label: {
    color: '#666',
  },
  value: {
    fontSize: 32,
    fontWeight: '600',
  },
  summary: {
    fontSize: 16,
    textAlign: 'center',
  },
});
