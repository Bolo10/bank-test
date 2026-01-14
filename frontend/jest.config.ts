import type { Config } from "jest";

const config: Config = {
  preset: "jest-preset-angular",
  testEnvironment: "jsdom",
  setupFilesAfterEnv: ["<rootDir>/setup-jest.ts"],
  transform: {
    "^.+\\.(ts|js|mjs|html)$": "jest-preset-angular",
  },
};

export default config;
