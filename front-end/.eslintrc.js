module.exports = {
    env: {
        browser: true,
        es6: true
    },
    rules : {
        'react/no-direct-mutation-state': 'warn', // state 직접 수정 금지
        'react/jsx-curly-brace-presence': 'warn', // jsx 내 불필요한 중괄호 금지
        'no-shadow' : 'off',
        'react/function-component-definition': [
          'off',
          {
            namedComponents: 'function-expression',
            unnamedComponents: 'function-expression',
          },
        ],
        'no-param-reassign': 'off',
        'no-return-assign': ['error', 'except-parens'],
        // A form label must be associated with a control 에러 해결
        "jsx-a11y/label-has-associated-control": ["error", {
          "required": {
            "some": ["nesting", "id"]
          }
        }],
        "jsx-a11y/label-has-for": ["error", {
          "required": {
            "some": ["nesting", "id"]
          }
        }],
        // jsx에서 js 파일 가져오기 허용
        "react/jsx-filename-extension": [1, { "extensions": [".js", ".jsx"] }],
        // 확장자명 명명 import 허용
        'import/extensions': [ 'off' ]
    },
    extends: ['airbnb', 'plugin:prettier/recommended'],
    parser: '@babel/eslint-parser'
}