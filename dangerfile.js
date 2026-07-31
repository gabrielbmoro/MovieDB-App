const { danger, warn } = require('danger');

const EXCLUDED_PATTERNS = [
  /\.md$/,
  /\.gitignore$/,
  /\/test\//,
  /\.(png|svg|jpg|jpeg|gif|webp)$/,
  /(package-lock|yarn\.lock|\.lock)$/,
  /\/build\//,
  /\/generated\//,
  /\/\.gradle\//,
  /\/node_modules\//,
];

const MAPPING_RULES = [
  {
    path: 'src/gradle/libs.versions.toml',
    knowledgeFiles: ['AGENTS.md', 'README.md'],
  },
  {
    path: 'src/build-logic/',
    knowledgeFiles: ['AGENTS.md'],
  },
  {
    path: 'src/settings.gradle.kts',
    knowledgeFiles: ['AGENTS.md'],
  },
  {
    path: 'src/ai/instructions/android.md',
    knowledgeFiles: ['AGENTS.md', 'src/androidApp/README.md'],
  },
  {
    path: 'src/ai/instructions/ios.md',
    knowledgeFiles: ['AGENTS.md', 'src/iosApp/README.md'],
  },
  {
    path: 'AGENTS.md',
    knowledgeFiles: ['README-AI-CONTEXT.md'],
  },
];

const KNOWN_MODULES = [
  'composeApp', 'data', 'domain', 'designsystem', 'platform',
  'feature-movies', 'feature-search', 'feature-tvshows', 'feature-wishlist',
  'androidApp', 'iosApp', 'build-logic',
];

function isExcluded(file) {
  return EXCLUDED_PATTERNS.some(pattern => pattern.test(file));
}

function getModule(file) {
  const match = file.match(/^src\/([^/]+)\//);
  return match ? match[1] : null;
}

const changedFiles = danger.git.modified_files.concat(danger.git.created_files);
const deletedFiles = danger.git.deleted_files;

const staleFiles = new Set();

for (const file of changedFiles) {
  let matched = false;

  for (const rule of MAPPING_RULES) {
    if (file.startsWith(rule.path)) {
      for (const kf of rule.knowledgeFiles) {
        if (!deletedFiles.includes(kf)) {
          staleFiles.add(kf);
        }
      }
      matched = true;
      break;
    }
  }

  if (matched) continue;
  if (isExcluded(file)) continue;

  const module = getModule(file);
  if (module && KNOWN_MODULES.includes(module)) {
    const readme = `src/${module}/README.md`;
    if (!deletedFiles.includes(readme)) {
      staleFiles.add(readme);
    }
  }
}

if (staleFiles.size > 0) {
  const list = Array.from(staleFiles).sort();
  warn(
    'The following knowledge files may need updating based on this PR\'s changes:\n\n' +
    list.map(f => `- \`${f}\``).join('\n')
  );
}
