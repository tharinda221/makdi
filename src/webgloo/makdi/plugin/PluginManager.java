package webgloo.makdi.plugin;

import webgloo.makdi.io.ObjectXmlBridge;
import webgloo.makdi.io.PluginXmlObject;
import webgloo.makdi.logging.MyTrace;
import webgloo.makdi.plugin.arcade.ArcadeGamesPlugin;
import webgloo.makdi.plugin.trends.google.GoogleHotTrendsPlugin;
import webgloo.makdi.plugin.microsite.MicroSitePlugin;
import webgloo.makdi.util.MyUtils;

/**
 *
 * @author rajeevj
 */
public class PluginManager {

    public static IPlugin getPluginBean(String pluginBeanFile) throws Exception {
        ObjectXmlBridge xmlobj = new ObjectXmlBridge();
        PluginXmlObject bean = xmlobj.decodeXStreamXmlFile(pluginBeanFile);

        if (MyUtils.isNullOrEmpty(bean.getSiteGuid())) {
            throw new Exception("Null or empty Profile site GUID");
        }
        if (MyUtils.isNullOrEmpty(bean.getAction())) {
            throw new Exception("Null or empty Action");
        }

        return bean;
    }

    public static void process(String pluginBeanFile) throws Exception {
        MyTrace.entry("PluginManager", "process(bean file)");
        process(getPluginBean(pluginBeanFile));
        MyTrace.exit("PluginManager", "process(bean file)");
    }

    private static void process(IPlugin pluginBean) throws Exception {
        //@todo - move to a processor factory

        if (pluginBean.getName().equals(IPlugin.GOOGLE_HOT_TRENDS)) {
            new GoogleHotTrendsPlugin().invoke(pluginBean);
        } else if (pluginBean.getName().equals(IPlugin.ARCADE_GAMES)) {
            new ArcadeGamesPlugin().invoke(pluginBean);
        } else if (pluginBean.getName().equals(IPlugin.MICRO_SITE)) {
            new MicroSitePlugin().invoke(pluginBean);
        } else {
            throw new Exception(pluginBean.getName() + " plugin not found ");
        }

    }
}
