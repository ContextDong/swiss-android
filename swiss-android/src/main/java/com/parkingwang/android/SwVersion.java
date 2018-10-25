package com.parkingwang.android;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author 董棉生(dongmiansheng@parkingwang.com)
 * @since 2018/10/24
 */


public class SwVersion {

    /**
     * 主版本
     */
    public final int major;
    /**
     * 副版本
     */
    public final int minor;
    /**
     * 修复版本
     */
    public final int revision;
    /**
     * 构建版本
     */
    public final int build;

    public final String name;

    public SwVersion(int major, int minor, int revision, int build, String name) {
        this.major = major;
        this.minor = minor;
        this.revision = revision;
        this.build = build;
        this.name = name;
    }

    public boolean isLowerThan(SwVersion version) {
        if (version == null) {
            return false;
        }
        if (major < version.major) {
            return true;
        }
        if (major == version.major) {
            if (minor < version.minor) {
                return true;
            }
            if (minor == version.minor) {
                if (revision < version.revision) {
                    return true;
                }
                if (revision == version.revision) {
                    return build < version.build;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SwVersion version = (SwVersion) o;

        if (major != version.major) {
            return false;
        }
        if (minor != version.minor) {
            return false;
        }
        if (revision != version.revision) {
            return false;
        }
        if (build != version.build) {
            return false;
        }
        return name != null ? name.equals(version.name) : version.name == null;
    }

    @Override
    public String toString() {
        return "Version{" +
                "major=" + major +
                ", minor=" + minor +
                ", revision=" + revision +
                ", name='" + name + '\'' +
                '}';
    }

    //                                           major        minor      revision     build
    private static final String VERSION_REGEX = "(\\d+)(?:\\.(\\d+)(?:\\.(\\d+)(?:\\.(\\d+))?)?)?.*";
    private static final Pattern sVersionPatter = Pattern.compile(VERSION_REGEX);

    public static SwVersion parse(String version) {
        if (version == null) {
            return null;
        }
        Matcher matcher = sVersionPatter.matcher(version);
        if (matcher.matches()) {
            int major = 0;
            int minor = 0;
            int revision = 0;
            int build = 0;

            String s = matcher.group(1);
            if (s != null) {
                major = Integer.parseInt(s);
            }

            s = matcher.group(2);
            if (s != null) {
                minor = Integer.parseInt(s);
            }

            s = matcher.group(3);
            if (s != null) {
                revision = Integer.parseInt(s);
            }

            s = matcher.group(4);
            if (s != null) {
                build = Integer.parseInt(s);
            }
            return new SwVersion(major, minor, revision, build, version);
        } else {
            return null;
        }
    }
}
