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
    {
      spec: `../../api-train-app/target/openapi/UserManagementApiSpec-merged.yaml`,
      output: '../src/generated/service-api/usermanagement/',
    },
  ];

  try {
    // wait for exec to complete
    for (const api of apis) {
      const { stdout, stderr } = await execPromise(
        `npx openapi --postfixServices Api --useOptions --input ${api.spec} --output ${api.output}`
      );

      const apiFile = api.output + "/core/OpenAPI.ts"
      const openAPI = fs.readFileSync(apiFile, "utf8")
      const openAPIWithAuthImport = "import { auth } from '$lib/Auth.svelte';\n" + openAPI
      const openAPIUsingAuthToken = openAPIWithAuthImport.replace("TOKEN: undefined,", "TOKEN: () => Promise.resolve(auth.getAccessToken() || ''),")

      fs.writeFileSync(apiFile, openAPIUsingAuthToken)


      const requestFile = api.output + "/core/request.ts"
      const request = fs.readFileSync(requestFile, "utf8")
      const requestWithKitErrorImport = "import { error as kitError } from \"@sveltejs/kit\";\n" + request
      const requestUsingKitError = requestWithKitErrorImport.replace(
        "throw new ApiError(options, result, error);",
        "kitError(result.status, { ...result.body } as any);").replace(
          "404: 'Not Found',",
          "404: 'Not Found', 409: 'Conflict', "
        )
      fs.writeFileSync(requestFile, requestUsingKitError)


      console.log('stdout:', stdout);
      console.log('stderr:', stderr);
    }
  } catch (error) {
    console.log(error);
  }
})();