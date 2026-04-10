# Push main + all feature/day2-* branches to remote "day2"
# Prereqs:
#   1. git remote add day2 https://github.com/commitverse2026/Culture-VastraVeda-Day2-Features.git
#   2. You must have push access (org member, PAT with repo scope, or push to your fork).
#   3. If the remote has unrelated history (e.g. old React template), use -ForceMain switch.

param(
    [switch] $ForceMain
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"
Set-Location (Split-Path -Parent $PSScriptRoot)

$branches = @(
    "main",
    "feature/day2-01-regional-directory",
    "feature/day2-02-clothing-map",
    "feature/day2-03-fabric-explorer",
    "feature/day2-04-festival-calendar",
    "feature/day2-05-occasion-guide",
    "feature/day2-06-clothing-quiz",
    "feature/day2-07-historical-timeline",
    "feature/day2-08-compare-outfits",
    "feature/day2-09-visual-gallery",
    "feature/day2-10-cultural-stories",
    "feature/day2-11-care-maintenance",
    "feature/day2-12-glossary",
    "feature/day2-13-featured-designers",
    "feature/day2-14-state-profiles",
    "feature/day2-15-modern-trends",
    "feature/day2-16-fabric-origins-tracker",
    "feature/day2-17-version-control",
    "feature/day2-18-ai-outfit-suggestions",
    "feature/day2-19-fabric-identifier",
    "feature/day2-20-open-clothing-api"
)

if (-not (git remote get-url day2 2>$null)) {
    git remote add day2 https://github.com/commitverse2026/Culture-VastraVeda-Day2-Features.git
}

if ($ForceMain) {
    git push -u day2 main --force
} else {
    git push -u day2 main
}

$featureBranches = $branches | Select-Object -Skip 1
foreach ($b in $featureBranches) {
    git push -u day2 $b
}
Write-Host "Done. Verify: https://github.com/commitverse2026/Culture-VastraVeda-Day2-Features/branches"
