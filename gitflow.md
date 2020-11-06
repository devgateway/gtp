# GTP Senegal Gitflow workflow

[A very nice explanation of the gitflow workflow](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow).

## Guidelines for developing a new task

- create from **develop** a new branch **task/JIRA-ID/task-short-description**
<br>Example: task/AD3-ANACIM-1/admin-setting
- commit atomic changes to this branch and [push the changes to GitHub](https://docs.github.com/en/free-pro-team@latest/github/using-git/pushing-commits-to-a-remote-repository) when the task is complete
- [create the pull request](https://docs.github.com/en/free-pro-team@latest/github/collaborating-with-issues-and-pull-requests/creating-a-pull-request) 
- mark JIRA ticket as RESOLVED and put it to UNASSIGNED
- pull requests [are closed by the project maintainer](https://www.atlassian.com/git/tutorials/making-a-pull-request/how-it-works)
- task branches will be deleted automatically once the pull request is merged

## Guidelines for developing a new feature

A feature branch is needed multiple sub-tasks must be combined before
integrating them into develop. Especially useful when multiple 
developers work in parallel on sub-tasks of a feature (API, UI, filters, etc). 

- create from **develop** a new branch **feature/JIRA-ID/feature-short-description**
<br>Example: feature/AD3-ANACIM-2/rainfall-chart
- push feature branch to GitHub and ask the project maintainer to [protect the branch](https://docs.github.com/en/enterprise-server@2.20/github/administering-a-repository/configuring-protected-branches)
- create task branches from this **feature/...** branch
- commit changes, push the task branch and create the pull request against the **feature/...** branch
- resolve the JIRA task and put it to UNASSIGNED
- project maintainer will close task pull request
- when all subtasks are done, remove feature branch protection 
and create a pull request from the feature branch against **develop**
- project maintainer will close feature pull request

## Guidelines for creating new releases

You need an early release branch only if some development has to continue on develop.

- create **release/vX.Y.Z** from **develop** Example: release/v1.0
- protect the release branch
- this release branch can be used for testing and bug fixing
  - create branches for bug fixes/improvements from **release** branch
  - make PRs against **both** release and develop branches    
  - integrate other changes for non-upcoming release to develop only. Note that breaking
  future changes can be rather done with a future/vX.Y.Z branch (see further).
- when the release is stable:
  - merge into **master**
  - tag master with **vX.Y.Z** version and [release in GitHub](https://docs.github.com/en/free-pro-team@latest/github/administering-a-repository/managing-releases-in-a-repository). Example: v1.0
  - delete the release branch
  
## Guidelines for creating a future branch

Temporary isolate breaking changes in a **future/vX.Y.Z** branch. 
Such branch should exist for up to a few months.

When:
- significant changes not part of the upcoming release(s)
- work need to continue on develop for other intermediate releases 

Guideline:
- create **future/vX.Y.Z** branch from develop, use the semantic version of that future release (not the upcoming one)
- implement "future" changes in task/feature branches from this **future** branch
- make PRs against **future** branch
- merge develop regularly to the future branch
- when no intermediate releases are needed, merge future into develop 
