import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { resolve } from 'path'

export default defineConfig({
  plugins: [react()],
  build: {
    // Build as a library so Spring Boot can serve it as a static file
    rollupOptions: {
      input: {
        classifica: resolve(__dirname, 'src/classifica.jsx'),
      },
      output: {
        entryFileNames: '[name].js',
        chunkFileNames: '[name].js',
        assetFileNames: '[name].[ext]',
        dir: '../src/main/resources/static/react',
        format: 'iife',
        globals: {
          react: 'React',
          'react-dom': 'ReactDOM',
        },
      },
      external: [],
    },
    outDir: '../src/main/resources/static/react',
    emptyOutDir: true,
  },
  server: {
    port: 5173,
    proxy: {
      '/api': 'http://localhost:8080',
    },
  },
})
