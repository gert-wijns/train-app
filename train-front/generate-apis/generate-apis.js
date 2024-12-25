var fs = require('fs');
var { exec } = require('node:child_process');
var util = require('node:util');
var execPromise = util.promisify(exec);

(async () => {
  const apis = [
    {
      spec: `../../api-train-app/target/openapi/PersonnelApiSpec-merged.yaml`,
      output: '../src/generated/service-api/personnel/',
    },
    {
      spec: `../../api-train-app/target/openapi/PlanningApiSpec-merged.yaml`,
      output: '../src/generated/service-api/planning/',
    },
    {
      spec: `../../api-train-app/target/openapi/AssetsApiSpec-merged.yaml`,
      output: '../src/generated/service-api/assets/',
    },
    {
      spec: `../../api-train-app/target/openapi/NetworkApiSpec-merged.yaml`,
      output: '../src/generated/service-api/network/',
    },
  ];

  try {
    // wait for exec to complete
    for (const api of apis) {
      const { stdout, stderr } = await execPromise(
        `npx openapi --postfixServices Api --useOptions --input ${api.spec} --output ${api.output}`
      );
      console.log('stdout:', stdout);
      console.log('stderr:', stderr);
    }
  } catch (error) {
    console.log(error);
  }
})();