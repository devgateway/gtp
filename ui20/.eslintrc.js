module.exports = {
  env: {
    browser: true,
    es6: true
  },
  extends: [
    'react-app'
  ],
  globals: {
    Atomics: 'readonly',
    SharedArrayBuffer: 'readonly'
  },
  parserOptions: {
    ecmaFeatures: {
      jsx: true
    },
    ecmaVersion: 2018,
    sourceType: 'module'
  },
  plugins: [
    'react'
  ],
  rules: {
    'keyword-spacing': ['error', {
      before: true, after: true
    }],
    'space-before-blocks': 'error',
    'space-infix-ops': 'error',
    'spaced-comment': 'error',
  }
}
