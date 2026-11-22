import { defineConfig } from '@rsbuild/core';

export default defineConfig({
  source: {
    entry: {
      index: './src/main.ts',
    },
  },
  html: {
    template: './src/index.html',
  },
  output: {
    // Everything is loaded synchronously through XHR, so keep hashed names for
    // cache busting but do not inject scripts we do not need.
    filenameHash: true,
  },
  performance: {
    chunkSplit: {
      strategy: 'all-in-one',
    },
  },
});
