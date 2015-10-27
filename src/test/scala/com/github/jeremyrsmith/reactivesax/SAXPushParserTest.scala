/**
 * Creative Commons Share-Alike 4.0
 *
 * This is a human-readable summary of (and not a substitute for) the license
 * (https://creativecommons.org/licenses/by-sa/4.0/)
 *
 * DISCLAIMER: This deed highlights only some of the key features and terms of the actual license. It is not a license
 * and has no legal value. You should carefully review all of the terms and conditions of the actual license before
 * using the licensed material.
 *
 * You are free to:
 *
 * Share — copy and redistribute the material in any medium or format
 * Adapt — remix, transform, and build upon the material for any purpose, even commercially.
 * The licensor cannot revoke these freedoms as long as you follow the license terms.
 *
 * Under the following terms:
 *
 * Attribution — You must give appropriate credit, provide a link to the license, and indicate if changes were made. You
 *   may do so in any reasonable manner, but not in any way that suggests the licensor endorses you or your use.
 * ShareAlike — If you remix, transform, or build upon the material, you must distribute your contributions under the
 *   same license as the original.
 * No additional restrictions — You may not apply legal terms or technological measures that legally restrict others
 *   from doing anything the license permits.
 *
 * Notices:
 *
 * You do not have to comply with the license for elements of the material in the public domain or where your use is
 * permitted by an applicable exception or limitation.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For
 * example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 *
 * Additional Restrictions:
 *
 * By using this software, you acknowledge that it has been provided without warranty or support.  Any damage of any
 * kind that results from the use of this software, whether indirectly or directly, is the sole responsibility of you,
 * the user of the software.  You agree that the original developer has no liability for any damage or other problems
 * arising from your use of the software, or an end-user's use of the software via the use of your project which uses
 * the software.
 *
 */

package com.github.jeremyrsmith.reactivesax

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FeatureSpec, Matchers, FlatSpec}
import org.xml.sax.{Attributes, Locator, ContentHandler}
import scala.language.dynamics
import scala.xml.Node

class SAXPushParserTest extends FeatureSpec with MockFactory with SAXTest {

  feature("Parses elements and attributes") {
    scenario("XML Document with elements, attributes, and text nodes") {

      val doc = <document>
        <element>Foo</element>
        <element2 attr="foo">Bar</element2>
      </document>

      doTest(doc)

    }

    scenario("XML Document with global namespace") {

      val doc = <document xmlns="http://foo.com/namespace">
        <element>Foo</element>
        <element2 attr="foo">Bar</element2>
      </document>

      doTest(doc)

    }

    scenario("XML Document with nested global namespaces") {
      val doc = <document xmlns="http://foo.com/namespace">
        <element xmlns="http://bar.com/namespace">Foo</element>
        <element2 attr="foo">Bar</element2>
      </document>

      doTest(doc)
    }

    scenario("XML Document with namespace prefixes") {
      val doc = <document xmlns:foo="http://foo.com/namespace">
        <foo:element>Foo</foo:element>
        <element2 attr="foo">Bar</element2>
      </document>

      doTest(doc)
    }

  }


  def doTest(doc: Node) = {
    val handler = mock[ContentHandler]
    handler expectsXML doc
    val parser = SAXPushParser(handler)
    parser.open()
    parser.write(doc.toString())
    parser.close()
  }


}