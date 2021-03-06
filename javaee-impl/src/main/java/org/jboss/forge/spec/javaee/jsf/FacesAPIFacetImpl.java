/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.spec.javaee.jsf;

import javax.inject.Inject;

import org.jboss.forge.project.dependencies.Dependency;
import org.jboss.forge.project.dependencies.DependencyBuilder;
import org.jboss.forge.project.dependencies.DependencyInstaller;
import org.jboss.forge.project.dependencies.ScopeType;
import org.jboss.forge.project.facets.DependencyFacet;
import org.jboss.forge.shell.ShellPrintWriter;
import org.jboss.forge.shell.plugins.Alias;
import org.jboss.forge.shell.plugins.RequiresFacet;
import org.jboss.forge.spec.javaee.FacesAPIFacet;
import org.jboss.forge.spec.javaee.ServletFacet;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@Alias("forge.spec.jsf.api")
@RequiresFacet(ServletFacet.class)
public class FacesAPIFacetImpl extends FacesFacetImpl implements FacesAPIFacet
{
   public static final Dependency JAVAEE6_FACES = DependencyBuilder
            .create("org.jboss.spec.javax.faces:jboss-jsf-api_2.0_spec").setScopeType(ScopeType.PROVIDED);
   public static final Dependency JAVAEE6_FACES_21 = DependencyBuilder
            .create("org.jboss.spec.javax.faces:jboss-jsf-api_2.1_spec").setScopeType(ScopeType.PROVIDED);

   @Inject
   public FacesAPIFacetImpl(final DependencyInstaller installer, final ShellPrintWriter out)
   {
       super(installer, out);
   }

   @Override
   public boolean isInstalled()
   {
      DependencyFacet deps = project.getFacet(DependencyFacet.class);
      return deps.hasEffectiveDependency(JAVAEE6_FACES) || deps.hasEffectiveDependency(JAVAEE6_FACES_21);
   }

   @Override
   public boolean install()
   {
      super.install();

      DependencyFacet deps = project.getFacet(DependencyFacet.class);
      if (!deps.hasDirectManagedDependency(JAVAEE6))
      {
         getInstaller().installManaged(project, JAVAEE6);
      }
      if(deps.hasEffectiveManagedDependency(JAVAEE6_FACES) && !deps.hasEffectiveDependency(JAVAEE6_FACES))
      {
         getInstaller().install(project, JAVAEE6_FACES);
      }
      else if(deps.hasEffectiveManagedDependency(JAVAEE6_FACES_21) && !deps.hasEffectiveDependency(JAVAEE6_FACES_21))
      {
         getInstaller().install(project, JAVAEE6_FACES_21);
      }

      return isInstalled();
   }

}
