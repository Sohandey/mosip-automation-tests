package io.mosip.ivv.e2e.methods;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import io.mosip.admin.fw.util.AdminTestUtil;
import io.mosip.ivv.core.base.StepInterface;
import io.mosip.ivv.core.exceptions.RigInternalError;
import io.mosip.ivv.orchestrator.BaseTestCaseUtil;
import io.mosip.ivv.orchestrator.PacketUtility;
import io.mosip.ivv.orchestrator.TestRunner;
import io.mosip.service.BaseTestCase;

public class SetContext extends BaseTestCaseUtil implements StepInterface {
	Logger logger = Logger.getLogger(SetContext.class);

	@Override
	public void run() throws RigInternalError {
		constantIntializer();
		String contextKeyValue = "dev_context";
		String userAndMachineDetailParam = null;
		String mosipVersion = null;
		boolean generatePrivateKey =Boolean.FALSE;
		if (step.getParameters() == null || step.getParameters().isEmpty() || step.getParameters().size() < 1) {
			logger.warn("SetContext Arugemnt is  Missing : Please pass the argument from DSL sheet");
		} else {
			contextKeyValue = step.getParameters().get(0);
			// contextKey.put("contextKey",contextKeyValue );
			contextKey.put(contextKeyValue, "true");
			contextInuse.clear();
			contextInuse.put("contextKey", contextKeyValue);
			if (step.getParameters().size() > 1) {   // machineid=112121@@.......
				String value = step.getParameters().get(1);
				if (!(value.equalsIgnoreCase("-1")))
					userAndMachineDetailParam = value;
			}
			if (step.getParameters().size() > 2) {   // 1@@2(mosip.version)
				List<String> version = PacketUtility.getParamsArg(step.getParameters().get(2), "@@");
				if (!(version.contains("-1")))
					mosipVersion = version.get(0) + "." + version.get(1);
			}
			if (step.getParameters().size() > 3)  // true/false  (want to generate privatekey)
				generatePrivateKey = Boolean.parseBoolean(step.getParameters().get(3));
			}
			packetUtility.createContexts(contextKeyValue, userAndMachineDetailParam, mosipVersion,
					generatePrivateKey, BaseTestCase.ApplnURI + "/");

		}
}
